package com.lupw.guava.retrying;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/3/8
 */
@Slf4j
@RestController
public class RetryController {

    private final RetryServiceImpl retryService;


    @Autowired
    public RetryController(RetryServiceImpl retryService) {
        this.retryService = retryService;
    }


    @GetMapping(value = "/spring/retry/test")
    public void springRetryTest() {
        // [Spring Retry Github](https://github.com/spring-projects/spring-retry)
        // [Spring Retry 常用示例](http://iyiguo.net/blog/2016/01/19/spring-retry-common-case/)
        // (1) 添加依赖
        // (2) Application 添加 @EnableRetry 注解, 支持重试功能
        retryService.doSomething();
    }


    @GetMapping(value = "/spring/retry/test2")
    public void springRetryTest2() {
        retryService.doSomething2();
    }


    @GetMapping(value = "/spring/tetry/test3")
    public void springRetryTest3() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // 简单重试策略, 重试到指定的次数, 这也是默认的策略
        // 这里因为没有配置回退策略, 这 10 次的重试 (包含第一的调用) 都是等上一次执行完成后就直接执行了
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(10);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        String result = null;
        try {
            result = retryTemplate.execute(new RetryCallback<String, Exception>() {
                @Override
                public String doWithRetry(RetryContext context) throws Exception {
                    log.info("doWithRetry retry count = {}", context.getRetryCount());
                    throw new RetryException("测试");
                }
            }, new RecoveryCallback<String>() {
                @Override
                public String recover(RetryContext context) throws Exception {
                    Throwable throwable = context.getLastThrowable();
                    log.info("error msg = {}", throwable.getMessage());
                    log.info("recover retry count = {}", context.getRetryCount());
                    return "失败";
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
