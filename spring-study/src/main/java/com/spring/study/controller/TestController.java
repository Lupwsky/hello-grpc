package com.spring.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lupw 2019-03-30
 */
@Slf4j
@RestController
public class TestController {

    private final Environment environment;

    /**
     * ConfigurableEnvironment 是 Environment 的子类, 注入的是同一个实例
     */
    private final ConfigurableEnvironment configurableEnvironment;

    /**
     * 这个注入的集合类型的 bean 是当前所有注册到容器中的 bean 的集合
     */
    private final Map<String, Object> systemProperties;

    private final BeanTest beanTest;

    @Autowired
    public TestController(Environment environment,
                          ConfigurableEnvironment configurableEnvironment,
                          Map<String, Object> systemProperties,
                          BeanTest beanTest) {
        this.environment = environment;
        this.configurableEnvironment = configurableEnvironment;
        this.systemProperties = systemProperties;
        this.beanTest = beanTest;
    }


    @GetMapping(value = "/test")
    public void test() {
        // 返回 /Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home
        log.info("JAVA_HOME = {}", environment.getProperty("JAVA_HOME"));

        // 返回 /Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home
        log.info("JAVA_HOME = {}", configurableEnvironment.getProperty("JAVA_HOME"));

        // 返回 true
        log.info("{}", environment.equals(configurableEnvironment));

        beanTest.test();

        log.info("{}", systemProperties.get("JAVA_HOME"));

        beanTest.test();
    }
}
