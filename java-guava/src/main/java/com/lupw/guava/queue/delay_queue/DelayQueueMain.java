package com.lupw.guava.queue.delay_queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;

/**
 * @author v_pwlu 2019/2/26
 */
@Slf4j
public class DelayQueueMain {

    // Java-DelayQueue知识整理
    // DelayQueue
    // * DelayQueue 内部使用的是 PriorityQueue 实现的, 同时实现了 BlockingQueue, BlockingQueue 是一个读写都是线程安全的队列
    // * 是一个先进先出的无界阻塞队列 (无界也不是完全的没有大小, 最大为 int 的最大值, 21 亿左右)
    // * 只有在延迟期满的时候才能从中获取到元素, 放在队列头部的是延迟期满后保存时间最长的 Delayed 元素
    // * 如果延迟都还没有期满, 则队列没有头部, poll 将返回 null

    // DelayQueue 使用场景
    // DelayQueue 可以用来实现延迟消息, 生产/消费队列, 如果不考虑分布式队列, 队列中的数据持久化, 消息可靠性问题 (虽然可以自己实现重试机制解决一定的问题, 但是如果正在处理某条消息时客户端崩溃还是导致这条消息丢失), DelayQueue 就可以满足要求

    // 放入 DelayQueue 的元素需要继承 Delayed 接口, Delayed扩展了 Comparable 接口, 用于比较延时的时间值
    // 见 DelayQueueItem

    // 使用示例
    // 放入数据到队列使用 offer 方法, 获取数据通常使用 take, 而不是 poll
    // 对所有的 BlockingQueue
    // take 方法在没有过期数据时会阻塞并释放资源, poll 会直接返回 null
    // offer 方法在队列空间不足的时候会直接返回 false, 而 put 则会阻塞等到队列有空间
    // 以上两点再使用的时候要注意, 尤其是在 poll 获取数据的时候, 我曾经因为开多线程 poll 数据, 在队列没有数据的时候导致 CPU 飙升

    public static void main(String[] args) {
        DelayQueue<DelayQueueItem> delayQueue = new DelayQueue<>();
        delayQueue.offer(new DelayQueueItem(3000, UserInfo.builder().username("lpw1").password("123").build()));
        delayQueue.offer(new DelayQueueItem(6000, UserInfo.builder().username("lpw2").password("123").build()));
        delayQueue.offer(new DelayQueueItem(9000, UserInfo.builder().username("lpw3").password("123").build()));

        DelayThread delayThread = new DelayThread(delayQueue);
        delayThread.start();

        try {
            delayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
