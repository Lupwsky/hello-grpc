package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.concurrent.*;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class CompletableFutureMain {

    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });

        future.thenAccept((result) -> {
            try {
                log.info("[MAIN] threadName = {}", Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.info("[MAIN] 不管 thenAccept 里面的方法是否执行完成, 我先执行");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("[MAIN] 程序结束");
    }
}


//log.info("[MAIN] threadName = {}, TAG-1", Thread.currentThread().getName());
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//        try {
//        log.info("[MAIN] threadName = {}, FUTURE-1", Thread.currentThread().getName());
//        Thread.sleep(2000);
//        log.info("[MAIN] threadName = {}, FUTURE-1 2s 过去了...", Thread.currentThread().getName());
//        } catch (InterruptedException e) {
//        e.printStackTrace();
//        }
//        return 100;
//        });
//
//        log.info("[MAIN] threadName = {}, TAG-2", Thread.currentThread().getName());
//        String str = future.thenApply((result) -> {
//        try {
//        log.info("[MAIN] threadName = {}, FUTURE-2", Thread.currentThread().getName());
//        Thread.sleep(1000);
//        log.info("[MAIN] threadName = {}, FUTURE-2 1s 过去了...", Thread.currentThread().getName());
//        } catch (InterruptedException e) {
//        e.printStackTrace();
//        }
//        return String.valueOf(result) + "1";
//        }).join();
//
//        log.info("[MAIN] threadName = {}, TAG-3", Thread.currentThread().getName());
//        log.info("[MAIN] threadName = {}, result = {}", Thread.currentThread().getName(), str);