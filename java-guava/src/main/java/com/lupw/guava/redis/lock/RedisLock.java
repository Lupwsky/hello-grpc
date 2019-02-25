package com.lupw.guava.redis.lock;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author lupw 2019-02-25
 */
@Slf4j
@Service
public class RedisLock {
    private final RedisClient redisClient;

    @Autowired
    public RedisLock(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    // 分布式锁可用的四个条件
    // * 互斥性, 同一时间只有一个客户端持有锁
    // * 无死锁, 即使有一个客户端在持有锁的期间崩溃而没有主动解锁, 也能保证后续其他客户端能加锁
    // * 容错性, 只要大部分节点正常运行, 客户端就可以自己加锁和解锁
    // * 标识性, 锁需要具备拥有者标识, 加锁和解锁必须是同一个客户端, 客户端不能解锁另一个客户端的锁

    // 错误加锁示例
    // 使用 setnx 和 expire 组合加锁, 这种方法可以有两个问题
    // * 满足分布式锁可用的四个条件的前三个条件, 第四点无法满足, 任意一个客户端都可以解锁
    // * 在执行 setnx 后设置过期时间时突然崩溃, 此时锁不会过期, 其他客户端一直不能获取到锁, 就会出现死锁

    public boolean errorLock1(String lockKey, String lockValue, int timeout) {
        try {
            StatefulRedisConnection<String, String> connection = redisClient.connect();
            RedisCommands<String, String> redisCommands = connection.sync();
            // 判断锁是否已被其他的客户端持有, 如果其他客户端有持有, 获取锁失败
            String lock = redisCommands.get(lockKey);
            if (lock == null || Objects.equals("", lock)) {
                return Boolean.FALSE;
            } else {
                redisCommands.setnx(lockKey, lockValue);
                redisCommands.expire(lockKey, timeout);
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("加锁错误, error = {}", e);
            return Boolean.FALSE;
        }
    }
}
