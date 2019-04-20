package com.lupw.guava.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/1/25
 */
@Slf4j
@RestController
public class RedisController {

    private final RedisServiceImpl redisService;


    @Autowired
    public RedisController(RedisServiceImpl redisService) {
        this.redisService = redisService;
    }


    @GetMapping(value = "/redis/test")
    public void redisDistributedServiceTest() {
        redisService.lettuceRedisSubscriber();
    }
}
