package com.lupw.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author v_pwlu 2019/1/17
 */
@Slf4j
public class GuavaBiMapMain {

    public static void main(String[] args) {
        biMapTest();
    }


    private static void biMapTest() {
        // BiMap 可以很方便的实现一个双向映射

        // 通常的做法需要维护两张表, 同步的时候也需要特别小心, 两边都要同步, 如果有一个没有同步, 很有可能造成程序上的异常
        Map<String, Integer> nameToIdMap = Maps.newHashMap();
        Map<Integer, String> idToNameMap = Maps.newHashMap();
        nameToIdMap.put("Bob", 42);
        idToNameMap.put(42, "Bob");

        // 此时 biMap 里面有 key1 = value1, key2 = value2 两组值
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("key1", "value1");
        biMap.put("key2", "value2");
        log.info(biMap.get("key1"));
        log.info(biMap.get("key2"));

        // 对于已经存在的值, 不允许使用新的键再次进行映射, 出现 IllegalArgumentException 异常
        try {
            biMap.put("key3", "value1");
        } catch (IllegalArgumentException e){
            log.info("error = {}", e);
        }

        // 如果有需要, 使用 forcePut 方法可以强制替换值对应的键, 重新进行映射, 此时有 key3 = value1, key2 = value2 两组值
        biMap.forcePut("key3", "value1");
        log.info(biMap.toString());

        // inverse() 反转 BiMap<K, V> 的键值映射
        log.info(biMap.get("key3"));

        // 此时有 value1 = key3, value2 = key2 两组值
        BiMap<String, String> inverseBiMap = biMap.inverse();
        log.info(inverseBiMap.get("value1"));

        // 对 biMap 添加新的键值对或者进行修改, 会直接同步到inverseBiMap
        // 此时 biMap 有 key3 = value1, key2 = value2, key4 = value4
        // 此时 inverseBiMap 有 value1 = key3, value2 = key2, value4 = key4
        // 同样对反转后的 inverseBiMap 进行修改也会直接同步到 biMap
        biMap.put("key4", "value4");
        biMap.forEach(log::info);
        inverseBiMap.forEach(log::info);

        // Guava 提供有 HashBiMap, ImmutableBiMap, EnumBiMap, EnumHashBiMap
    }
}
