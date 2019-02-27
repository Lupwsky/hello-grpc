package com.lupw.guava.queue.blocking_queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author v_pwlu 2019/2/26
 */
@Slf4j
@Service
public class BlockingQueueService {

    // [解读 Java 并发队列 BlockingQueue](http://www.importnew.com/28053.html)
    // BlockingQueue 是一个先进先出的队列
    // BlockingQueue 支持当获取队列元素但是队列为空时, 会阻塞等待队列中有元素再返回
    // BlockingQueue 也支持添加元素时, 如果队列已满, 那么等到队列可以放入新元素时再放入


}
