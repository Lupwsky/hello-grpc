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
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        }).whenComplete((result, error) -> log.info("[Main] result = {}, error = {}", result, error.getMessage()));

        try {
            DateTime startTime = DateTime.now();
            int finalResult = future1.get(10, TimeUnit.SECONDS);
            DateTime endTime = DateTime.now();
            log.info("[MAIN] finalResult = {}, time = {}ms", finalResult, endTime.getMillis() - startTime.getMillis());
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}