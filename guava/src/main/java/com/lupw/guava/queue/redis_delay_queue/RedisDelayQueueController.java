package com.lupw.guava.queue.redis_delay_queue;

import com.alibaba.fastjson.JSONObject;
import com.lupw.guava.queue.UserInfo;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
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
        redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 3, Optional.ofNullable(value1)));
        redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 5, Optional.ofNullable(value2)));
        redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 7, Optional.ofNullable(value3)));

        String luaScript = "local resultArray = redis.call('zrangebyscore', KEYS[1], ARGV[1], ARGV[2], 'limit' , ARGV[3], ARGV[4])\n" +
                "if #resultArray > 0 then\n" +
                "    if redis.call('zrem', KEYS[1], resultArray[1]) > 0 then\n" +
                "        return resultArray[1]\n" +
                "    else\n" +
                "        return ''\n" +
                "    end\n" +
                "else\n" +
                "    return ''\n" +
                "end";

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    String value = redisCommands.eval(luaScript, ScriptOutputType.VALUE, new String[]{key}, "0", String.valueOf(DateTime.now().getMillis()), "0", "1");
                    log.info("value = {}", value);

                    if (value == null || Objects.equals("", value)) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    } else {
                        // TODO 开始业务处理
                    }
                }
            }
        }.start();

        // 再这个需求里面, 有些参数是固定的, Lua 脚本可以直接更改为如下, 调用的时候可以少传三个参数

        String luaScript1 = "local resultArray = redis.call('zrangebyscore', KEYS[1], 0, ARGV[1], 'limit' , 0, 1)\n" +
                "if #resultArray > 0 then\n" +
                "    if redis.call('zrem', KEYS[1], resultArray[1]) > 0 then\n" +
                "        return resultArray[1]\n" +
                "    else\n" +
                "        return ''\n" +
                "    end\n" +
                "else\n" +
                "    return ''\n" +
                "end";

        // 调用的时候少传三个参数
        redisCommands.eval(luaScript, ScriptOutputType.VALUE, new String[]{key}, String.valueOf(DateTime.now().getMillis()));

    }
}
