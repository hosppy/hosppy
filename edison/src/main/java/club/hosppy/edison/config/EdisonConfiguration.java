package club.hosppy.edison.config;

import club.hosppy.edison.core.balancer.LoadBalancer;
import club.hosppy.edison.core.balancer.RandomLoadBalancer;
import club.hosppy.edison.core.filter.FaviconFilter;
import club.hosppy.edison.core.filter.HealthCheckFilter;
import club.hosppy.edison.core.http.HttpClientProvider;
import club.hosppy.edison.core.http.RequestDataExtractor;
import club.hosppy.edison.core.http.RequestForwarder;
import club.hosppy.edison.core.http.ReverseProxyFilter;
import club.hosppy.edison.core.interceptor.CacheResponseInterceptor;
import club.hosppy.edison.core.interceptor.PostForwardResponseInterceptor;
import club.hosppy.edison.core.interceptor.PreForwardRequestInterceptor;
import club.hosppy.edison.core.mapping.ConfigurationMappingProvider;
import club.hosppy.edison.core.mapping.MappingProvider;
import club.hosppy.edison.core.mapping.MappingValidator;
import club.hosppy.edison.core.trace.LoggingTraceInterceptor;
import club.hosppy.edison.core.trace.ProxyingTraceInterceptor;
import club.hosppy.edison.core.trace.TraceInterceptor;
import club.hosppy.edison.view.AssetLoader;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(EdisonProperties.class)
public class EdisonConfiguration {

    private final EdisonProperties edisonProperties;

    private final AssetLoader assetLoader;

    @Bean
    public FilterRegistrationBean<ReverseProxyFilter> faradayReverseProxyFilterRegistrationBean(
            ReverseProxyFilter proxyFilter) {
        FilterRegistrationBean<ReverseProxyFilter> registrationBean = new FilterRegistrationBean<>(proxyFilter);
        registrationBean.setOrder(edisonProperties.getFilterOrder()); // by default to Ordered.HIGHEST_PRECEDENCE + 100
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<FaviconFilter> faviconFilterFilterRegistrationBean() {
        FilterRegistrationBean<FaviconFilter> registrationBean
                = new FilterRegistrationBean<>(new FaviconFilter(assetLoader.getFaviconBytes()));
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 75);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<HealthCheckFilter> healthCheckFilterFilterRegistrationBean() {
        FilterRegistrationBean<HealthCheckFilter> registrationBean =
                new FilterRegistrationBean<>(new HealthCheckFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 70);
        return registrationBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public ReverseProxyFilter faradayReverseProxyFilter(
            RequestDataExtractor extractor,
            MappingProvider mappingProvider,
            RequestForwarder requestForwarder,
            ProxyingTraceInterceptor traceInterceptor,
            PreForwardRequestInterceptor requestInterceptor
    ) {
        return new ReverseProxyFilter(extractor, mappingProvider,
                requestForwarder, traceInterceptor, requestInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestDataExtractor faradayRequestDataExtractor() {
        return new RequestDataExtractor();
    }

    @Bean
    @ConditionalOnMissingBean
    public MappingProvider faradayConfigurationMappingsProvider(MappingValidator mappingValidator,
                                                                HttpClientProvider httpClientProvider) {
        //if (edisonProperties.isEnableProgrammaticMapping()) {
        //    return new ProgrammaticMappingsProvider(
        //            envConfig, serverProperties,
        //            faradayProperties, mappingValidator,
        //            httpClientProvider);
        //} else {
        //    return new ConfigurationMappingsProvider(
        //            edisonProperties, mappingValidator,
        //            httpClientProvider);
        //}
        return new ConfigurationMappingProvider(
                edisonProperties, mappingValidator,
                httpClientProvider);
    }


    @Bean
    @ConditionalOnMissingBean
    public LoadBalancer faradayLoadBalancer() {
        return new RandomLoadBalancer();
    }

    @Bean
    @ConditionalOnMissingBean
    public MappingValidator faradayMappingsValidator() {
        return new MappingValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestForwarder faradayRequestForwarder(
            HttpClientProvider httpClientProvider,
            MappingProvider mappingProvider,
            LoadBalancer loadBalancer,
            MeterRegistry meterRegistry,
            ProxyingTraceInterceptor traceInterceptor,
            PostForwardResponseInterceptor responseInterceptor
    ) {
        return new RequestForwarder(
                edisonProperties, httpClientProvider,
                mappingProvider, loadBalancer, meterRegistry,
                traceInterceptor, responseInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpClientProvider faradayHttpClientProvider() {
        return new HttpClientProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProxyingTraceInterceptor proxyingTraceInterceptor(TraceInterceptor traceInterceptor) {
        return new ProxyingTraceInterceptor(traceInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public TraceInterceptor traceInterceptor() {
        return new LoggingTraceInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public PreForwardRequestInterceptor preForwardRequestInterceptor() {
        return (data, mapping) -> {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public PostForwardResponseInterceptor postForwardResponseInterceptor() {
        return new CacheResponseInterceptor();
    }
}
