package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class FutureMain {

    public static class DelayTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return Thread.currentThread().getName();
        }
    }


    // 创建线程的 2 种方式, 一种是直接继承 Thread, 另外一种就是实现 Runnable 接口, 这两种方式在执行完任务之后无法获取执行结果
    // 从 Java 1.5 开始，就提供了 Callable 和 Future 两个类, 通过它们可以在任务执行完毕之后得到任务执行结果
    // Future 可以监视目标线程调用 call 的情况, 当你调用 Future 的 get 方法以获获取结果时, 当前线程就开始阻塞, 直到 call 方法结束返回结果

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
