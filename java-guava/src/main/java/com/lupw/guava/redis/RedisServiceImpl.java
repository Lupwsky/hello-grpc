package com.lupw.guava.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

            /**
             * 接收到消息时的回调方法
             *
             * @param s  channel
             * @param s2 接收到的消息
             */
            @Override
            public void message(String s, String s2) {
                log.info("message, channel = {}, message = {}", s, s2);
            }

            /**
             * 接收到消息时的回调方法
             *
             * @param s  Pattern
             * @param k1 channel
             * @param s2 接收到的消息
             */
            @Override
            public void message(String s, String k1, String s2) {
                log.info("message, pattern = {}, channel = {}, message = {}", s, k1, s2);
            }

            /**
             * 订阅 channel 时的监听方法
             *
             * @param s channel
             * @param l 当前 channel 的数量
             */
            @Override
            public void subscribed(String s, long l) {
                log.info("subscribed, channel = {}, currentChannelCount = {}", s, l);
            }

            /**
             * 批量订阅 channel 时的监听方法, 根据这里订阅的 channel 数量被多次调用
             *
             * @param s channel
             * @param l 当前 channel 的数量
             */
            @Override
            public void psubscribed(String s, long l) {
                log.info("psubscribed, channel = {}, currentChannelCount = {}", s, l);
            }

            /**
             * 取消订阅 channel 时的监听方法
             *
             * @param s channel
             * @param l 当前 channel 的数量
             */
            @Override
            public void unsubscribed(String s, long l) {
                log.info("unsubscribed, channel = {}, currentChannelCount = {}", s, l);
            }

            /**
             * 批量取消订阅 channel 时的监听方法, 根据这里取消订阅的 channel 数量被多次调用
             *
             * @param s channel
             * @param l 当前 channel 的数量
             */
            @Override
            public void punsubscribed(String s, long l) {
                log.info("punsubscribed, channel = {}, currentChannelCount = {}", s, l);
            }
        });

        RedisPubSubCommands<String, String> sync = connection.sync();

        // 订阅单个 channel, RedisPubSubListener 方法的 subscribed 会被调用
        sync.subscribe("username");

        // 订阅多个 channel, RedisPubSubListener 方法的 psubscribed 会根据这里订阅的 channel 数量被多次调用
        sync.psubscribe("username1", "username2");


        RedisCommands<String, String> commands = redisClient.connect().sync();
        commands.publish("username", "lpw");
    }
}
