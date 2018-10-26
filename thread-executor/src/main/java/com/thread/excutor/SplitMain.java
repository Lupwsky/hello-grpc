package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/9/19
 */
@Slf4j
public class SplitMain {

    public static void main(String[] args) {

        String str = "";
        String[] strs = str.split("\\.");
        log.info(Arrays.toString(strs));
        String b= strs[0];
        log.info(b);
    }
}
