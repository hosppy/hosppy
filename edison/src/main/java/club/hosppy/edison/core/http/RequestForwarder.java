package club.hosppy.edison.core.http;

import club.hosppy.edison.config.EdisonProperties;
import club.hosppy.edison.config.MappingProperties;
import club.hosppy.edison.core.balancer.LoadBalancer;
import club.hosppy.edison.core.interceptor.PostForwardResponseInterceptor;
import club.hosppy.edison.core.mapping.MappingProvider;
import club.hosppy.edison.core.trace.ProxyingTraceInterceptor;
import club.hosppy.edison.exceptions.EdisonException;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.System.nanoTime;
import static java.time.Duration.ofNanos;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.ResponseEntity.status;

@AllArgsConstructor
public class RequestForwarder {

    private static final ILogger log = SLoggerFactory.getLogger(RequestForwarder.class);

    protected final EdisonProperties edisonProperties;
    protected final HttpClientProvider httpClientProvider;
    protected final MappingProvider mappingProvider;
    protected final LoadBalancer loadBalancer;
    protected final MeterRegistry meterRegistry;
    protected final ProxyingTraceInterceptor traceInterceptor;
    protected final PostForwardResponseInterceptor postForwardResponseInterceptor;

    public ResponseEntity<byte[]> forwardHttpRequest(RequestData data, String traceId, MappingProperties mapping) {
        ForwardDestination destination = resolveForwardDestination(data.getUri(), mapping);
        prepareForwardedRequestHeaders(data, destination);
        traceInterceptor.onForwardStart(traceId, destination.getMappingName(),
                data.getMethod(), data.getHost(), destination.getUri().toString(),
                data.getBody(), data.getHeaders());
        RequestEntity<byte[]> request = new RequestEntity<>(data.getBody(), data.getHeaders(), data.getMethod(), destination.getUri());
        ResponseData response = sendRequest(traceId, request, mapping, destination.getMappingMetricsName(), data);

        log.debug(String.format("Forwarded: %s %s %s -> %s %d", data.getMethod(), data.getHost(), data.getUri(), destination.getUri(),
                response.getStatus().value()));

        traceInterceptor.onForwardComplete(traceId, response.getStatus(), response.getBody(), response.getHeaders());
        postForwardResponseInterceptor.intercept(response, mapping);
        prepareForwardedResponseHeaders(response);

        return status(response.getStatus())
                .headers(response.getHeaders())
                .body(response.getBody());

    }

    /**
     * Remove any protocol-level headers from the remote server's response that
     * do not apply to the new response we are sending.
     *
     * @param response
     */
    protected void prepareForwardedResponseHeaders(ResponseData response) {
        HttpHeaders headers = response.getHeaders();
        headers.remove(TRANSFER_ENCODING);
        headers.remove(CONNECTION);
        headers.remove("Public-Key-Pins");
        headers.remove(SERVER);
        headers.remove("Strict-Transport-Security");
    }

    /**
     * Remove any protocol-level headers from the clients request that
     * do not apply to the new request we are sending to the remote server.
     *
     * @param request
     * @param destination
     */
    protected void prepareForwardedRequestHeaders(RequestData request, ForwardDestination destination) {
        HttpHeaders headers = request.getHeaders();
        headers.remove(TE);
    }

    protected ForwardDestination resolveForwardDestination(String originUri, MappingProperties mapping) {
        return new ForwardDestination(createDestinationUrl(originUri, mapping), mapping.getName(), resolveMetricsName(mapping));
    }

    protected URI createDestinationUrl(String uri, MappingProperties mapping) {
        String host = loadBalancer.chooseDestination(mapping.getDestinations());
        try {
            return new URI(host + uri);
        } catch (URISyntaxException e) {
            throw new EdisonException("Error creating destination URL from HTTP request URI: " + uri + " using mapping " + mapping, e);
        }
    }

    protected ResponseData sendRequest(String traceId, RequestEntity<byte[]> request, MappingProperties mapping,
                                       String mappingMetricsName, RequestData requestData) {
        ResponseEntity<byte[]> response;
        long startingTime = nanoTime();
        try {
            response = httpClientProvider.getHttpClient(mapping.getName()).exchange(request, byte[].class);
            recordLatency(mappingMetricsName, startingTime);
        } catch (HttpStatusCodeException e) {
            recordLatency(mappingMetricsName, startingTime);
            response = status(e.getStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsByteArray());
        } catch (Exception e) {
            recordLatency(mappingMetricsName, startingTime);
            traceInterceptor.onForwardFailed(traceId, e);
            throw e;
        }
        return new ResponseData(response.getStatusCode(), response.getHeaders(), response.getBody(), requestData);
    }

    protected void recordLatency(String metricName, long startingTime) {
        if (meterRegistry != null) {
            meterRegistry.timer(metricName).record(ofNanos(nanoTime() - startingTime));
        }
    }

    protected String resolveMetricsName(MappingProperties mapping) {
        return edisonProperties.getMetrics().getNamesPrefix() + "." + mapping.getName();
    }
}
