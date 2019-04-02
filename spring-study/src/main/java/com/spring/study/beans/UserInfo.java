package com.spring.study.beans;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author v_pwlu 2018/11/8
 */
@Slf4j
@Data
public class UserInfo implements ApplicationContextAware {
    private String name;
    private String email;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
        log.info("获取到 applicationContext 资源");
    }
}
