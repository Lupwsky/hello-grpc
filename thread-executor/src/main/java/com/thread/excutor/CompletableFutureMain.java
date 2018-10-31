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
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });

        future1.runAfterEither(future2, () -> log.info("[MAIN] 所有计算完成")).join();

//        // 不调用其中一个 future 的 get 方法, Runnable 的 run 方法就不会执行, 注释下面的代码, 会返现没有执行 run 方法
//        try {
//            DateTime startTime = DateTime.now();
//            int finalResult = future2.get(10, TimeUnit.SECONDS);
//            DateTime endTime = DateTime.now();
//            log.info("[MAIN] finalResult = {}, time = {}ms", finalResult, endTime.getMillis() - startTime.getMillis());
//        } catch (InterruptedException | TimeoutException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}