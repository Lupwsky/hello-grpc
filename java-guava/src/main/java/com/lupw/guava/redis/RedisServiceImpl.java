package com.lupw.guava.redis;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author v_pwlu 2019/1/25
 */
@Slf4j
@Service
public class RedisServiceImpl {


    private final RedisCommands<String, String> stringSyncRedisCommands;


    @Autowired
    public RedisServiceImpl(RedisCommands<String, String> stringSyncRedisCommands) {
        this.stringSyncRedisCommands = stringSyncRedisCommands;
    }


    void lettuceRedisTest() {
        stringSyncRedisCommands.set("username", "lpw");
        stringSyncRedisCommands.expire("username", 3000);
        long timeout = stringSyncRedisCommands.ttl("username");
        String value = stringSyncRedisCommands.get("username");
        log.info("[Lettuce Redis] timeout = {}, value = {}", timeout, value);
    }



    private void lettuceRedisSubscriber() {
        // 订阅者
    }
}
