package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.concurrent.*;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class CompletableFutureMain {

    public static class DelayTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(5000);
            return Thread.currentThread().getName();
        }
    }


    public static void main(String[] args) {

        DateTime startTime = DateTime.now();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new DelayTask());

        try {
            String threadName = future.get(1, TimeUnit.SECONDS);
            log.info("[Future 测试] threadName = {}", threadName);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        
        DateTime endTime = DateTime.now();
        log.info("[Future 测试] 总耗时 = {}ms", endTime.getMillis() - startTime.getMillis());

        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
