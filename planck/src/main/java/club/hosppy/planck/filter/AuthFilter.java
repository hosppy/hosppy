package club.hosppy.planck.filter;

import club.hosppy.common.auth.AuthConstant;
import club.hosppy.common.crypto.Sign;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class AuthFilter implements GlobalFilter, Ordered {

    private static final ILogger logger = SLoggerFactory.getLogger(AuthFilter.class);

    @Value("${hosppy.signing-secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String token = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION_HEADER);
        if (isBlank(token)) {
            return unauthorizedPage(response);
        }
        try {
            Sign.verify(token, secret);
        } catch (JWTVerificationException e) {
            logger.info("Invalid token: " + token, e);
            return unauthorizedPage(response);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> unauthorizedPage(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.TEXT_HTML);
        DataBuffer buffer = response.bufferFactory().wrap("Unauthorized".getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
