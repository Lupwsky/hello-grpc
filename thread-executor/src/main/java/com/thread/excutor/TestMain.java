package com.thread.excutor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.*;
import java.util.function.Function;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class TestMain {
    public static void main(String[] args) {
        String ch = "你好吗?";
        String en = "Hello";

        log.info("ch = {}", ch.length());
        log.info("en = {}", en.length());
    }
}
