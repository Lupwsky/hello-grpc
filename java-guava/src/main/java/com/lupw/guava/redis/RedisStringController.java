package com.lupw.guava.redis;

import com.google.common.collect.Maps;
import io.lettuce.core.RedisClient;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author v_pwlu 2019/1/31
 */
@Slf4j
@RestController
public class RedisStringController {

    private final RedisClient redisClient;


    @Autowired
    public RedisStringController(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    /**
     * 注意: 在使用 Redis 的操作指令时, 要先考虑各个指令的时间复杂度, 下面的指令没有说明的时间复杂度均为 O(1)
     *
     * Redis 客户端 Lettuce 操作基础数据
     * Redis 有 5 种基础数据结构，分别为:
     *     string (字符串)
     *     list (列表)
     *     set (集合)
     *     hash (哈希)
     *     zset (有序集合)
     */
    @GetMapping(value = "/redis/string/test")
    public void dataStructTest() {
        stringTest1();
        bitmapTest();
    }


    /**
     * 字符串的 set 操作
     */
    private void stringTest1() {
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisStringCommands<String, String> redisCommands = connection.sync();

        // ## set 添加键值, set key value
        // (1) 对已有值的 key 会覆写旧值, 无视类型
        // (2) 对带有 ttl 的可以, 此操作会清除和替代原有 ttl
        // redisCommands 的大多数 set 方法成功返回字符串 OK, 失败返回 null
        redisCommands.set("username-set", "lpw-set");

        // ## mset, msetnx 批量添加键值, mset key1 value1, key2, key2
        // 注意批量设置 Redis 没有提供设置过期时间的接口, 如果需要设置过期时间使用 set 循环添加
        Map<String, String> map = Maps.newHashMap();
        map.put("username-mset", "lpw-mset");
        map.put("password-mset", "lpw-pwd-mset");
        redisCommands.mset(map);
        redisCommands.msetnx(map);

        // ## set 参数 Redis 2.6.12 版本开始, set 命令可以在后面添加一些参数, 如下
        // (1) ex second, 设置键的过期时间单位为秒, second 为时间参数, 例 set key value ex 5, 等同于 setex key 5 value (set key 5 value 会报错)
        // (2) px millisecond, 设置键的过期时间单位为毫秒, millisecond 为时间参数, 例 set key value px 5000, 等同于 psetex key 5000 value
        // (3) nx, key 不存在时进行设置操作, 存在时则不会进行任何操作, 等同于 setnx key value
        // (4) xx, key 已经存在时才对键进行设置操作, 注意 Redis 没有 setxx 指令
        redisCommands.set("username-set-args", "lpw-set-args", SetArgs.Builder.px(5000));

        // ## setex 对键进行设置操作并设置过期时间, setex key second value
        // (1) 设置过期时间, 单位为秒
        redisCommands.setex("username-setex", 10,"lpw-setex");

        // (2) set 值后再使用 expire 设置过期时间
        // setex 是原子操作, set 值后再使用 expire 设置过期时间是非原子操作
        redisCommands.set("username-expire", "lpw-expire");
        ((RedisCommands<String, String>) redisCommands).expire("username-expire", 10);

        // ## psetex, 对键进行设置操作并设置过期时间, 时间单位为毫秒, setex key millisecond value
        redisCommands.psetex("username-psetex", 10000, "lpw-psetex");

        // ## setnx key 不存在时进行设置操作, key 存在时则不会进行任何操作, key value
        redisCommands.set("username-setnx", "lpw-setnx");

        // ## getset, 将给定 key 的值设为 value, 并返回 key 的旧值, getset key value
        // 如果不是字符串类型, 则报错, 如果 key 值不存在返回 null
        redisCommands.getset("username-set", "lpw-get-set");

        // ## append 将 value 追加到 key 原来的值的末尾
        // 不存在就新建, 返回最终 value 的长度
        // 不是字符串类型则报错, redisCommands.append 方法会抛出异常还是返回 0 ? 等待验证
        long appendResult = redisCommands.append("username-set", "-append");
        log.info("appendResult = {}", appendResult);

        // ## incr, 如果值是一个整数类型, 该指令可以让值加 1, 但是最大的值不不能超过 long 类型的大小, incr key
        // 如果 key 值不存在, 则新建 key 并设置值为 0 后再加 1, 该指令返回最终的值
        redisCommands.set("count", "20");
        long incrResult = redisCommands.incr("count");
        // 运用这种特性可以完成类似计数器, 限流功能, http://redisdoc.com/string/incr.html

        // ## incrby, 如果值是一个整数类型, 该指令可以让值加指定的数值, incrby key num
        // 如果 key 值不存在, 则新建 key 并设置值为 0 后再加 1, 该指令返回最终的值
        redisCommands.set("count", "20");
        long incrByResult = redisCommands.incrby("count", 10);

        // ## incrfloat, 将值和浮点型的数相加, incrfloat key floatnNum
        // 使用 redis 时返回的时字符串, redisCommands.incrbyfloat 方法进行了封装, 返回的是浮点型数据
        // 关于 incrfloat, http://redisdoc.com/string/incrbyfloat.html
        redisCommands.set("count", "20");
        Double incrbyfloatResult = redisCommands.incrbyfloat("count", 5.5);

        // ## decr, decrby 和 decrbyfloat
        // 和 incr, incrby 相反, 减去数值, 但是要注意的是没有 decrbyfloat 指令

        // ## 字符串 bitmap 相关指令
        // https://segmentfault.com/a/1190000008188655
    }


    /**
     * 位图
     */
    private void bitmapTest() {
        // https://juejin.im/book/5afc2e5f6fb9a07a9b362527/section/5b330620e51d4558e03ce7f8
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisStringCommands<String, String> redisCommands = connection.sync();

        // ## 设置值-零存
        // 设置的时候从高位往低位设置
        redisCommands.setbit("bitmap", 0, 0);
        redisCommands.setbit("bitmap", 1, 1);
        redisCommands.setbit("bitmap", 2, 1);
        redisCommands.setbit("bitmap", 4, 1);
        redisCommands.setbit("bitmap", 9, 1);
        redisCommands.setbit("bitmap", 10, 1);
        redisCommands.setbit("bitmap", 13, 1);
        redisCommands.setbit("bitmap", 15, 1);

        // ## 设置值-整存
        // 整存的方法和存放普通的字符串并无差别, 如下, 和上面使用零存的方式存入的值时一致的
        redisCommands.set("bitmap", "he");

        // ## 获取值-整取
        log.info("value = {}", redisCommands.get("bitmap"));

        // 读取的时候从低位的第一位非 0 位开始的往高位读取, 高位不足的部分补零
        // 将上例中的第 16 位设置为 0, 结果是一样的
        redisCommands.setbit("bitmap", 16, 0);
        log.info("value = {}", redisCommands.get("bitmap"));

        // offset 参数必须大于或等于 0 且小于 2^32, bit 映射被限制在 512 MB 之内
        // offset 越大消耗的内存也越大, 尽量避免大的 offset 的值, 大量的大内存分配很可能造成 Redis 服务器被阻塞

        // ## 获取值-零取
        // 获取指定 key 所存储的字符串的偏移量的位的值, 0 或者 1, 当 key 不存在或者 offset 超出当前的位数范围, 均返回 0
        log.info("value = {}", redisCommands.getbit("bitmap", 1));

        // ## BITCOUNT
        // BITCOUNT key [start] [end]
        // 时间复杂度 O(N)
        // http://redisdoc.com/bitmap/bitcount.html
        // bitcount 指令用于统计给定的 key 对应的值的比特位被设置为 1 的数量
        // 如上例中, 字符串 "he" 的比特位被设置为 1 的数量为 7
        log.info("value = {}", redisCommands.bitcount("bitmap"));

        // 可以设置参数 [start, end], 统计指定字节范围内比特位被设置为 1 的数量
        // 字符串 "he" 的第 0 字节开始位至第 0 字节结束位的比特位被设置为 1 的数量为 3
        // 字符串 "he" 的第 1 字节开始位至第 1 字节结束位的比特位被设置为 1 的数量为 4
        // 字符串 "he" 的第 0 字节开始位至第 1 字节结束位的比特位被设置为 1 的数量为 7
        // 字符串 "he" 的第 1 字节开始位至第 2 字节结束位的比特位被设置为 1 的数量为 4, 这种情况和第二种一样, 统计时对超出的字节补 0 计算
        log.info("value = {}", redisCommands.bitcount("bitmap", 0, 0));
        log.info("value = {}", redisCommands.bitcount("bitmap", 1, 1));
        log.info("value = {}", redisCommands.bitcount("bitmap", 0, 1));
        log.info("value = {}", redisCommands.bitcount("bitmap", 1, 2));

        // 参数 [start, end] 可以为负值, 如 -1 表示倒数第一个字节
        log.info("value = {}", redisCommands.bitcount("bitmap", 0, -1));

        // ## BITOPS
        // BITOPS key bit [start] [end]
        // 时间复杂度 O(N)
        // http://redisdoc.com/bitmap/bitpos.html
        // 返回第一个比特位为 1 或者为 0 的位置, 如果 key 对应的值不存在返回 -1
        // 也可以设置参数 [start, end], 返回第一个比特位为 1 或者为 0 的位置
        redisCommands.setbit("bitops", 3, 1);
        redisCommands.setbit("bitops", 10, 1);
        redisCommands.setbit("bitops", 15, 1);

        // 返回第一个比特位为 1 的位置, 此处值为 3
        log.info("value = {}", redisCommands.bitpos("bitops", true));

        // 返回第一个比特位为 0 的位置, 此处值为 0
        log.info("value = {}", redisCommands.bitpos("bitops", true));

        // key 对应的值不存在返回 -1
        log.info("value = {}", redisCommands.bitpos("none", true));

        // ## BITOP
        // http://redisdoc.com/bitmap/bitop.html
        // 时间复杂度 O(N)
        // BITOP AND destkey key [key ...], 对一个或多个 key 求逻辑并，并将结果保存到 destkey
        // BITOP OR destkey key [key ...], 对一个或多个 key 求逻辑或，并将结果保存到 destkey
        // BITOP XOR destkey key [key ...], 对一个或多个 key 求逻辑异或，并将结果保存到 destkey\
        // BITOP NOT destkey key, 对给定 key 求逻辑非，并将结果保存到 destkey
        // 除了 NOT 操作之外, 其他操作都可以接受一个或多个 key 作为输入
        // 当 BITOP 处理不同长度的字符串时, 较短的那个字符串或者对应的 key 没有值字符串, 所缺少的部分会被看作 0
        // 以上指令最终保存的字符串长度是输入的 key 对应的最长的字符串的长度
        redisCommands.bitopOr("destkey", "bitmap", "bitops");
    }
}


















