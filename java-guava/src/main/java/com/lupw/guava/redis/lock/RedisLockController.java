package com.lupw.guava.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/2/26
 */
@Slf4j
@RestController
public class RedisLockController {

    private final RedisLock redisLock;

    @Autowired
    public RedisLockController(RedisLock redisLock) {
        this.redisLock = redisLock;
    }


    @GetMapping(value = "/test/lock")
    public void lock() {
        String lockKey = "lock";
        String lockValue = "lock_id";
        redisLock.lock3(lockKey, lockValue, 600);
    }


    @GetMapping(value = "/test/unlock")
    public void unlock() {
        String lockKey = "lock";
        String lockValue = "lock_id";
        redisLock.unlock(lockKey, lockValue);
    }
}
