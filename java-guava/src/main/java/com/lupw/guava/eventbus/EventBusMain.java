package com.lupw.guava.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @author v_pwlu 2019/1/21
 */
@Slf4j
public class EventBusMain {

    public static void main(String[] args) {
        // Java 的进程内事件分发都是通过发布者和订阅者之间的显式注册实现的
        // Guava 的 EventBus 可以实现进程内事件分发, 使组件间有了更好的解耦
        // 注意 EventBus 不适用于进程间通信
        // Guava 为我们提供了同步事件 EventBus 和异步实现 AsyncEventBus 两个事件总线

        // (1) 订阅事件详见 EventBusSubscriber
        // (2) 通过 EventBus.post() 来发布事件

        // 同步调用
        EventBus eventBus = new EventBus();
        eventBus.register(new EventBusSubscriber());
        eventBus.post("Hello, Guava EventBus");

        // 异步调用
        AsyncEventBus asyncEventBus = new AsyncEventBus(new SimpleAsyncTaskExecutor());
        asyncEventBus.register(new EventBusSubscriber());
        asyncEventBus.post("Hello, Guava EventBus");

        // 测试死亡 DeadEvent
        eventBus.post("DeadEvent");

        // [eventBusListener] thread = main, value = Hello, Guava EventBus
        // [eventBusListener] thread = main, value = DeadEvent
        // [eventBusListener] thread = SimpleAsyncTaskExecutor-1, value = Hello, Guava EventBus
    }
}
