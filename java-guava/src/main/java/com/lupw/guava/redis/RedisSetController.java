package com.lupw.guava.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author v_pwlu 2019/1/31
 */
@Slf4j
@RestController
public class RedisSetController {

    private final RedisClient redisClient;


    @Autowired
    public RedisSetController(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    @GetMapping(value = "/redis/set/test")
    public void dataStructTest() {
        test();
    }



    private void test() {
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> redisCommands = connection.sync();


    }
}


















