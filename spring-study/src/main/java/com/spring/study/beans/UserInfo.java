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
public class UserInfo implements ApplicationContextAware, InitializingBean, DisposableBean {
    private String name;
    private String email;
    private ApplicationContext applicationContext;

    public void init() {
        log.info("UserInfo init");
    }


    public void destro1() {
        log.info("UserInfo destroy");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("UserInfo init");
    }


    @Override
    public void destroy() throws Exception {
        log.info("UserInfo destroy");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
        log.info("获取到 applicationContext 资源");
    }
}
