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

    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 2);
    }

    @Bean
    public MyGateWayFilter myGateWayFilter() {
        return new MyGateWayFilter();
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, RedisRateLimiter redisRateLimiter) {
        return builder.routes()
                .route("cloud-provider0", r -> r.path("/get/username/from/9020")
                        .filters(f -> f.filter(new MyGateWayFilter()))
                        .uri("http://localhost:9020"))
                .build();
    }
}
