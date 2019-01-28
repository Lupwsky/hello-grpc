package com.lupw.guava.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author v_pwlu 2019/1/25
 */
@Slf4j
@Configuration
public class RedisConfiguration {

    @Bean(destroyMethod = "shutdown")
    public RedisClient getRedisClient() {
        return RedisClient.create("redis://lupengwei.4585@127.0.0.1:6379/0?timeout=10s");
    }
}
