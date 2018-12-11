package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author v_pwlu 2018/11/29
 */
@Slf4j
public class StreamExceptionMain {

    public static void main(String[] args) {
        List<Integer> dataList = Arrays.asList(1, 0, 1, 0);
        List<Integer> tempList = dataList.stream().map(StreamExceptionMain::mod).collect(Collectors.toList());
        log.info("[TEST] {}", tempList.toString());
    }

    private static int mod(int data) {
        try {
            return  2/ data;
        } catch (Exception e) {
            log.error("[TEST] {}", e.getMessage());
            return 0;
        }
    }
}
