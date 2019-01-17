package com.lupw.guava;

import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;


/**
 * @author v_pwlu 2019/1/17
 */
@Slf4j
public class GuavaClassToInstanceMapMain {

    public static void main(String[] args) {

    }


    private static void classToInstanceMapTest() {
        // ClassToInstanceMap 是一种特殊的 Map, 它的键是类型, 而值是符合键所指类型的对象对象, 用法和普通的 Map 没有区别
        // Guava 提供了两种有用的实现, MutableClassToInstanceMap 和 ImmutableClassToInstanceMap
        ClassToInstanceMap<String> classToInstanceMap = MutableClassToInstanceMap.create();
        classToInstanceMap.put(String.class, "1");
        log.info(classToInstanceMap.get(String.class));

        // 将值更改为 2
        classToInstanceMap.put(String.class, "2");
        log.info(classToInstanceMap.get(String.class));
    }
}
