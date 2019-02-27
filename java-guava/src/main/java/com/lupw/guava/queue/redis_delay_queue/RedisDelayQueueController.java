package com.lupw.guava.queue.redis_delay_queue;

import com.alibaba.fastjson.JSONObject;
import com.lupw.guava.queue.UserInfo;
import io.lettuce.core.Limit;
import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author v_pwlu 2019/2/27
 */
@Slf4j
@RestController
public class RedisDelayQueueController {

    private final RedisClient redisClient;

    @Autowired
    public RedisDelayQueueController(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    @GetMapping(value = "/redis/delay/queue/test")
    public void redisDelayQueueTest() {
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> redisCommands = connection.sync();

        String key = "delay_queue";
        // 实际开发建议使用业务 ID 和随机生成的唯一 ID 作为 value, 随机生成的唯一 ID 可以保证消息的唯一性, 业务 ID 可以避免 value 携带的信息过多
        String value1 = JSONObject.toJSONString(UserInfo.builder().username("lpw1").password("123").build());
        String value2 = JSONObject.toJSONString(UserInfo.builder().username("lpw2").password("123").build());
        String value3 = JSONObject.toJSONString(UserInfo.builder().username("lpw3").password("123").build());
        redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 10, Optional.ofNullable(value1)));
        redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 15, Optional.ofNullable(value2)));
        redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 20, Optional.ofNullable(value3)));

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    List<String> resultList;

                    // 只获取第一条数据, 只获取不会移除数据
                    resultList = redisCommands.zrangebyscore(key, Range.create(0, DateTime.now().getMillis()), Limit.create(0, 1));
                    if (resultList.size() == 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    } else {
                        // 移除数据获取到的数据
                        if (redisCommands.zrem(key, resultList.get(0)) > 0) {
                            log.info("userInfo = {}", resultList.get(0));
                        }
                    }
                }
            }
        }.start();
    }
}
