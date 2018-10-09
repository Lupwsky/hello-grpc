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
public class Main {

    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        for (int i = 0; i < 10; i++) {
//            executorService.execute(new TestRunnable());
//        }
//        executorService.shutdown();

        String str = "";
        String[] strs = str.split("\\.");
        log.info(Arrays.toString(strs));
        String b= strs[0];
        log.info(b);
    }


    static class TestRunnable implements Runnable {

        @Override
        public void run() {
            log.info("{} 线程被调用了", Thread.currentThread().getName());
        }
    }
}
