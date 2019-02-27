package com.lupw.guava.queue.delay_queue;

import com.lupw.guava.queue.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author v_pwlu 2019/2/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelayQueueItem implements Delayed {

    /**
     * 延迟时间, 单位毫秒
     */
    private long delay;


    /**
     * 过期时间, 单位毫秒
     */
    private long expire;


    /**
     * 保存在队列中的数据
     */
    private UserInfo userInfo;


    public DelayQueueItem(long delay, UserInfo userInfo) {
        this.delay = delay;
        this.expire = System.currentTimeMillis() * 1000 + delay;
        this.userInfo = userInfo;
    }


    /**
     * 当一个元素的 getDelay(TimeUnit.NANOSECONDS) 方法返回一个小于等于 0 的值时, 将发生到期, 此元素将放到队列头部
     * [关于 getDelay 方法没有使用 TimeUnit 转换造成性能问题](http://www.blogjava.net/killme2008/archive/2010/10/22/335897.html)
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
    }


    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) -o.getDelay(TimeUnit.MILLISECONDS));
    }
}
