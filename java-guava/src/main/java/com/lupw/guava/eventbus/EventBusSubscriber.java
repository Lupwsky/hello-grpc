package com.lupw.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author v_pwlu 2019/1/21
 */
@Slf4j
public class EventBusSubscriber {

    @Subscribe
    public void eventBusListener(String event) {
        // 订阅一个时间很简单, 在方法上面添加一个 @Subscribe 和保证只有一个输入参数的方法就可
        // Guava 发布的事件默认不会处理线程安全的, 可以使用注解标注 @AllowConcurrentEvents 来保证其线程安全
        log.info("[eventBusListener] thread = {}, value = {}", Thread.currentThread().getName(), event);
    }


    @Subscribe
    public void deadEventBusListener(DeadEvent event) {
        // 如果 EventBus 发送的消息都不是订阅者关心的称之为 DeadEvent, 即没没有任何一个订阅者订阅这个事件
        // 实现一个 DeadEvent 只需要保证方法有一个参数且参数类型为 DeadEvent 即可
        log.info("[deadEventBusListener] thread = {}, value = {}", Thread.currentThread().getName(), event);
    }
}
