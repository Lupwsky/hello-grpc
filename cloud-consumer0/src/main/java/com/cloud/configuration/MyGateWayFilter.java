package com.cloud.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @author v_pwlu 2019/5/17
 */
@Slf4j
public class MyGateWayFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // pre
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        String hostName = Optional.ofNullable(remoteAddress)
                .orElse(new InetSocketAddress("000.000.000.000", 0))
                .getHostName();
        log.info("remote address = {}", hostName);

        // post
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            exchange.getFormData().hasElement().subscribe(value -> log.info("has element = {}", value));
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
