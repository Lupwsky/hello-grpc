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


    @Bean
    public RedisClient getRedisClient() {
        return RedisClient.create("redis://lupengwei.4585@10.98.164.156:6380/0?timeout=10s");
    }


    @Bean
    RedisCommands<String, String> getStringSyncRedisCommands(RedisClient redisClient) {
        return redisClient.connect().sync();
    }


    @Bean
    StatefulRedisPubSubConnection<String, String> getStatefulRedisPubSubConnection(RedisClient redisClient) {
        return redisClient.connectPubSub();
    }
}
