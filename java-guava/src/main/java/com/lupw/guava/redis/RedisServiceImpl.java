package com.lupw.guava.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author v_pwlu 2019/1/25
 */
@Slf4j
@Service
public class RedisServiceImpl {

    private final RedisClient redisClient;


    @Autowired
    public RedisServiceImpl(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    public void lettuceRedisSubscriber() {
        // 同步方式
        StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
        connection.addListener(new RedisPubSubListener<String, String>() {

            @Override
            public void message(String s, String s2) {
                log.info("message, {}, {}", s, s2);
            }

            @Override
            public void message(String s, String k1, String s2) {
                log.info("message, {}, {}, {}", s, k1, s2);
            }

            @Override
            public void subscribed(String s, long l) {
                log.info("subscribed, {}, {}", s, l);
            }

            @Override
            public void psubscribed(String s, long l) {
                log.info("psubscribed, {}, {}", s, l);
            }

            @Override
            public void unsubscribed(String s, long l) {
                log.info("unsubscribed, {}, {}", s, l);
            }

            @Override
            public void punsubscribed(String s, long l) {
                log.info("punsubscribed, {}, {}", s, l);
            }
        });

        RedisPubSubCommands<String, String> sync = connection.sync();
        sync.subscribe("username");
    }
}
