package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }


    private static void test1() {
        // isPresent 用于判断对象的实例是否存在, true 表示存在, false 表示不存在, 即值为 null
        Optional<DataNode> dataNodeOptional = Optional.empty();
        if (dataNodeOptional.isPresent()) {
            log.info("[Optional isPresent 方法] dataNode = {}", dataNodeOptional.get().toString());
        } else {
            log.info("[Optional isPresent 方法] dataNode 的值为 null");
        }
    }


    private static void test2() {
        // ifPresent 如果实例存在, 执行其他的操作, 这里调用将会没有任何的输出
        Optional<DataNode> dataNodeOptional = Optional.empty();
        dataNodeOptional.ifPresent(data -> log.info("[Optional isPresent 方法] dataNode = {}", data.toString()));
    }


    private static void test3() {
        // orElse 方法, 如果为 null 就返回一个默认的对象实例
        Optional<DataNode> dataNodeOptional = Optional.empty();
        DataNode dataNode = dataNodeOptional.orElse(DataNode.builder().name("DEFAULT").soc(1).build());
        log.info("[Optional orElse 方法] dataNode = {}", dataNode.toString());
    }


    private static void test4() {
        // orElseGet 方法, 和 orElse 方法一样, 如果为 null 就返回一个默认的对象实例
        // 只不过 orElseGet 方法接收的是一个 Supplier 接口的实例, 是一个 Lambda 表达式
        // 我们可以这样使用 return dataNode.orElseGet(() -> getFromDatabase());
        Optional<DataNode> dataNodeOptional = Optional.empty();
        DataNode dataNode = dataNodeOptional.orElseGet(() -> DataNode.builder().name("DEFAULT").soc(1).build());
        log.info("[Optional orElseGet 方法] dataNode = {}", dataNode.toString());
    }


    private static void test5() {
        // orElseThrow 如果为 null 就抛出一个异常
        Optional<DataNode> dataNodeOptional = Optional.empty();
        try {
            DataNode dataNode = dataNodeOptional.orElseThrow(() -> new IllegalArgumentException("对象的值为 null"));
            log.info("[Optional orElseThrow 方法] dataNode = {}", dataNode.toString());
        } catch (Exception e) {
            log.error("[Optional orElseThrow 方法] error = {}", e.getMessage());
        }
    }


    private static void test6() {
        // map 和 filter 的方法的使用在 Stream API 里面介绍过, 这里不详细介绍了
        // Optional<DataNode> dataNodeOptional = Optional.empty();
        Optional<DataNode> dataNodeOptional = Optional.of(DataNode.builder().name("DEFAULT").soc(1).build());
        Optional<String> nameOptional = dataNodeOptional.map((DataNode::getName));
        log.info("[Optional map 方法] name = {}", nameOptional.orElse(""));
    }
}
