package com.lupw.guava.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author v_pwlu 2019/1/25
 */
@Slf4j
@Service
public class RedissonDistributedServiceImpl {

//    private final RedissonClient redissonClient;
//
//    @Autowired
//    public RedisDistributedServiceImpl(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
//
//
//    /**
//     * 注意, 注意, 注意: SpringBoot 整合 Redisson 时, 使用内嵌 Tomcat 启动错误
//     * 在 SpringBoot 启动的 main 函数中增加 TomcatURLStreamHandlerFactory.disable();
//     * 见 https://blog.csdn.net/u012476983/article/details/78183706
//     */
//    public void redisDistributedLockTest() {
//        // 同步获取可重入锁
//        RLock lock = redissonClient.getLock("lock");
//        try {
//            // 参数1: 获取锁最多等待时常
//            // 参数2: 上锁后自动解锁的时间
//            // 参数3: 时间单位
//            boolean isLockGet = lock.tryLock(5, 10, TimeUnit.SECONDS);
//            if (isLockGet) {
//                log.info("[REDIS LOCK] 获取锁成功");
//            } else {
//                log.info("[REDIS LOCK] 获取锁失败");
//            }
//        } catch (InterruptedException e) {
//            log.info("[REDIS LOCK] 获取锁失败, error = {}", e);
//        } finally {
//            lock.unlock();
//        }
//    }
}
