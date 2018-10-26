package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class TestMain {

    public static class DelayTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return Thread.currentThread().getName();
        }
    }


    public static void main(String[] args) {
        List<Future<String>> futureList = new ArrayList<>();

        // public static ExecutorService newCachedThreadPool() {
        //    return new ThreadPoolExecutor(0,                 // 0 个核心线程
        //                                  Integer.MAX_VALUE, // 2147483647 个非核心线程
        //                                  60L,               // 非核心线程空闲超过 60s 销毁
        //                                  TimeUnit.SECONDS,
        //                                  new SynchronousQueue<Runnable>());
        // }

        DateTime startTime = DateTime.now();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            futureList.add(executorService.submit(new DelayTask()));
        }

        // Future 可以监视目标线程调用 call 的情况, 当你调用 Future 的 get 方法以获得结果时, 当前线程就开始阻塞, 直到 call 方法结束返回结果
        futureList.forEach(future -> {
            try {
                String threadName = future.get();
                log.info("[Future 测试] threadName = {}", threadName);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        DateTime endTime = DateTime.now();
        log.info("[Future 测试] 总耗时 = {}ms", endTime.getMillis() - startTime.getMillis());

        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
