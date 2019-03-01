package com.lupw.guava.redis.limiting;

import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author v_pwlu 2019/2/28
 */
@Slf4j
@RestController
public class RedisLimitingController {

    private final RedisClient redisClient;

    @Autowired
    public RedisLimitingController(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    @GetMapping(value = "/redis/limiting/test")
    public Boolean redisLimitingTest() {
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> redisCommands = connection.sync();

        String key = String.format("LIMITING:%s:%s", "LPW001", "LOGIN");
        long currTime = DateTime.now().getMillis();

        // 移除 1 秒之前的历史数据
        redisCommands.zremrangebyscore(key, Range.create(0, (double) (currTime - 1000)));

        // 获取当前用户存在于时间窗口的数据的数量
        List<String> limitingDataList = redisCommands.zrange(key, 0, currTime);

        // 每秒请求不能超过 5 次请求
        if (limitingDataList.size() >= 5) {
            redisCommands.expire(key, 600);
            log.info("[简单限流测试] 超过限流阈值");
            return false;
        } else {
            String value = UUID.randomUUID().toString().replace("-", "");
            redisCommands.zadd(key, (double) currTime, value);
            redisCommands.expire(key, 600);
            return true;
        }
    }

    // 为了保证操作的原子性, 可以使用执行 Lua 脚本的方式来实现上面的功能
}
