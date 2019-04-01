package com.spring.study.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author v_pwlu 2019/3/25
 */
@Slf4j
public class DefineClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

    DefineClassPathXmlApplicationContext(String... configLocations) throws BeansException {
        super(configLocations);
    }

    // 这个方法在源码中时没有任何实现的, 用户可以重写 initPropertySources 方法来实现自己的验证属性的逻辑, 是 Spring 开放式结构的体现

    @Override
    protected void initPropertySources() {
        log.info("Spring 初始化之前的准备工作, 一般用于对环境变量进行检测或者对系统属性进行设置和检测");

        // 系统的相关属性, JVM 启动的时候使用 -D 参数可以添加自定义的属性值
        Map<String, Object> jvmProperties = this.getEnvironment().getSystemProperties();
        jvmProperties.forEach((key, value) -> log.info("key = {}, value = {}", key, value));

        // 系统的环境变量
        Map<String, Object> systemProperties = this.getEnvironment().getSystemEnvironment();
        systemProperties.forEach((key, value) -> log.info("key = {}, value = {}", key, value));

        // 自己手动检测, 检测不存在手动抛出异常
        String javaHome = "JAVA_HOME";
        if (Objects.equals(null, systemProperties.get(javaHome)) || Objects.equals("", systemProperties.get(javaHome))) {
            throw new RuntimeException("JAVA_HOME 没有配置");
        }

        // 调用 setRequiredProperties() 方法后, 通过 validateRequiredProperties() 方法来检测
        // 如果该属性不存在则抛出 MissingRequiredPropertiesException 异常
        // 假设 JAVA_HOME 属性不存在抛出的异常提示 = MissingRequiredPropertiesException: The following properties were declared as required but could not be resolved: [JAVA_HOME]
        this.getEnvironment().setRequiredProperties("JAVA_HOME");
    }
}
