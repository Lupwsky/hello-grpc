package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class TestMain {
    public static void main(String[] args) {
        List<String> dataList = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
        String value1 = List2StringUtils.list2String(dataList, ";");
        log.info(value1);

        String value2 = List2StringUtils.list2String(dataList, ";", "[", "]");
        log.info(value2);
    }
}
