package com.lupw.guava.queue.delay_queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;

/**
 * @author v_pwlu 2019/2/27
 */
@Slf4j
public class DelayThread extends Thread {

    private DelayQueue<DelayQueueItem> delayQueue;

    public DelayThread(DelayQueue<DelayQueueItem> delayQueue) {
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // take 方法会阻塞在这里, 知道有过期的元素
                DelayQueueItem delayQueueItem = delayQueue.take();
                log.info("userInfo = {}", delayQueueItem.getUserInfo());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
