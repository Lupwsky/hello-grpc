package com.lupw.guava.java;

import com.google.common.collect.Lists;
import com.lupw.guava.queue.UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author v_pwlu 2019/3/19
 */
@Slf4j
public class StreamIndexMain {

    public static void main(String[] args) {
        List<UserInfo> dataList = Lists.newArrayList(UserInfo.builder().build(), UserInfo.builder().build(), UserInfo.builder().build());

        // 普通的 for 循环
        for (int i = 0; i < dataList.size(); i++) {
            log.info("index = {}, username = {}", i, dataList.get(i).getUsername());
        }

        // Java 8 中 Stream 流实现一个遍历并使用 index
        IntStream.range(0, dataList.size()).forEach(i -> log.info("index = {}, username = {}", i, dataList.get(i).getUsername()));

        // 如果需要使用索引同时转换成其他的类型, 使用 mapToObj 实现
        List<UserInfo> userInfoList = IntStream.range(0, dataList.size())
                .mapToObj(i -> UserInfo.builder().password(String.valueOf(i)).build())
                .collect(Collectors.toList());
    }

}
