package com.cloud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;


/**
 * @author v_pwlu 2019/5/13
 */
@Configuration
public class RouteLocatorConfig {

    private final RedisRateLimiter redisRateLimiter;

    @Autowired
    public RouteLocatorConfig(RedisRateLimiter redisRateLimiter) {
        this.redisRateLimiter = redisRateLimiter;
    }

    @Bean
    public RequestRateLimiterGatewayFilterFactory requestRateLimiterGatewayFilterFactory() {
        return new RequestRateLimiterGatewayFilterFactory(new RedisRateLimiter(1, 300),
                (KeyResolver) exchange -> {
                    // 使用 IP 进行限流
                    return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
                });
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cloud-provider0", r -> r.path("/get/username/from/9020")
                        .filters(f -> f.requestRateLimiter(config -> config
                                // defaultReplenishRate = 每秒处理请求数, defaultBurstCapacity = 令牌桶数量
                                .setRateLimiter(redisRateLimiter)
                                .setKeyResolver((KeyResolver) exchange -> {
                                    // 使用 IP 进行限流
                                    return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
                                })))
                        .uri("http://localhost:9020"))
                .build();
    }
}
