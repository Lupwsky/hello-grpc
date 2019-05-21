package com.lupw.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author v_pwlu 2019/5/21
 */
@Configuration
public class GuavaRateLimiterConfig {

    @Bean
    public RateLimiter rateLimiter() {
        // 每秒多生成 1 个令牌, 直到稳定后每秒生成 5 个令牌
        // 即第 1 秒生成 1 个令牌, 第 2 秒生成 2 个令牌, 第 3 秒生成 3 个令牌, 直到第 5 秒生成 5 个令牌到达稳定状态
        return  RateLimiter.create(5, 1, TimeUnit.SECONDS);
    }

}
