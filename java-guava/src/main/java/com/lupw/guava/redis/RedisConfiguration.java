package com.lupw.guava.redis;

import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;


import java.time.Duration;

/**
 * @author v_pwlu 2019/1/25
 */
@Slf4j
@Configuration
public class RedisConfiguration {

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        // https://www.cnblogs.com/koushr/p/9211801.html
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
        redisStandaloneConfiguration.setPassword(RedisPassword.of("lupengwei.4585"));
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(10000))
                .build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
    }

    @Bean(destroyMethod = "shutdown")
    public RedisClient getRedisClient() {
        return RedisClient.create("redis://lupengwei.4585@127.0.0.1:6379/0?timeout=10s");
    }
}
