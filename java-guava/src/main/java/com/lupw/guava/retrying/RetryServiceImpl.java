package com.lupw.guava.retrying;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author v_pwlu 2019/3/8
 */
@Slf4j
@Service
public class RetryServiceImpl {

    // @Retryable(RetryException.class) 注解表示调用这个方法, 如果出现 RetryException 异常就重试, `默认最多重试 3 次, 重试间隔为 1 秒`

    @Retryable(RetryException.class)
    public void doSomething() {
        throw new RetryException("抛出异常");
    }


    // 如果最后经过重试的次数还是没有成功, 就执行加了 @Recover 注解的方法

    @Recover
    public void doSomethingIfUnsuccessful() {
        log.info("任务执行失败");
    }
}
