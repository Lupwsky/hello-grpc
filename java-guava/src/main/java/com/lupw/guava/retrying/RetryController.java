package com.lupw.guava.retrying;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        // [Spring Retry 常用示例](http://iyiguo.net/blog/2016/01/19/spring-retry-common-case/)
        // [Spring Retry Github](https://github.com/spring-projects/spring-retry)
        // (1) 添加依赖
        // (2) Application 添加 @EnableRetry 注解, 支持重试功能
        retryService.doSomething();
    }
}
