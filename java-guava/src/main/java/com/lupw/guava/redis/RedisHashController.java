package com.lupw.guava.redis;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author v_pwlu 2019/1/31
 */
@Slf4j
@RestController
public class RedisHashController {

    private final RedisClient redisClient;


    @Autowired
    public RedisHashController(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    @GetMapping(value = "/redis/hash/test")
    public void dataStructTest() {
        test();
    }


    private void test() {
        String key = "user:TF001";
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> redisCommands = connection.sync();

        // ## HSET
        // HSET key field value
        // 成功设置返回 1, 已经存在并且覆盖旧值返回0, 失败返回错误
        redisCommands.hset(key, "name", "lpw");
        redisCommands.hset(key, "age", "18");
        redisCommands.hset(key, "sex", "男");

        // ## HSETNX
        // HSETNX key field value
        // 当 key 对应的 Hash 表不存在 field 时, 设置这个值, 如果存在则不做任何操作
        // 成功时返回 1, 失败或者放弃操作返回 0
        boolean hsetnxResult = redisCommands.hsetnx("user:TF001", "name", "LPW");
        log.info("hsetnxResult = {}", hsetnxResult);

        // ## HMSET
        // HMSET key field value [field value …]
        // 批量设置 key 对应的 Hash 表中的值, 如果已经存在, 则覆盖, 设置成功返回 OK, 设置失败返回错误
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "LPW");
        hashMap.put("age", "20");
        String hmsetResult = redisCommands.hmset(key, hashMap);
        log.info("hmsetResult = {}", hmsetResult);

        // ## HGET
        // HGET hash field
        // 返回 Hash 表中指定 field 的 value, 对于不存在的 field 返回 null
        redisCommands.hget(key, "name");

        // ## HMGET
        // HMGET key field [field …]
        // 批量获取 Hash 表指定的字段
        List<KeyValue<String, String>> keyValueList = redisCommands.hmget(key, "name", "age", "none");
        KeyValue<String, String> keyValue = keyValueList.get(0);
        log.info("key = {}, value = {}", keyValue.getKey(), keyValue.getValue());
        keyValue = keyValueList.get(2);
        log.info("key = {}, value = {}", keyValue.getKey(), keyValue.getValueOrElse(""));

        // ## HGETALL
        // HGETALL key
        // 返回 key 对应 Hash 表中所有的键值对
        Map<String, String> resultMap = redisCommands.hgetall(key);
        log.info("hset = {}", resultMap.toString());

        // ## HEXISTS
        // HEXISTS key field
        // 检测 key 对应的 Hash 表中 field 是否存在, 存在返回 1, 不存在返回 0
        redisCommands.hexists(key, "name");

        // ## HDEL
        // HDEL key field [field …]
        // 删除 key 对应的 Hash 表中对应的 field, 返回成功删除的数量
        redisCommands.hdel(key, "name");
        redisCommands.hset(key, "name", "lpw");

        // ## HKEYS
        // HKEYS key
        // 返回 key 对应的 Hash 表中所有的 field, 没有任何 field 或者 key 不存在返回一个空表
        List<String> keyList = redisCommands.hkeys(key);
        log.info("keyList = {}", keyList.toString());

        // ## HVALS
        // HVALS key
        // // 返回 key 对应的 Hash 表中所有的值, 没有任何 field 或者 key 不存在返回一个空表
        List<String> valueList = redisCommands.hvals(key);
        log.info("valueList = {}", valueList.toString());

        // ## HLEN
        // HLEN key
        // key 对应的 Hash 表中 field 的数量
        redisCommands.hlen(key);

        // ## HSTRLEN
        // HSTRLEN key field
        // 返回 key 对应的 Hash 表中所有的 field 对应值的字符串长度
        redisCommands.hstrlen(key, "name");

        // ## HINCRBY 和 HINCRBYFLOAT
        // HINCRBY key field increment - 加减整型数据
        // HINCRBYFLOAT key field increment - 加减浮点型数据
        // key 对应的 Hash 表中 field 的字段做加减操作, 如果 filed 对应的值如果是字符串, 会出现错误
        redisCommands.hincrby(key, "age", 2);
    }
}


















