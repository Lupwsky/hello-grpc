package com.spring.study.beans;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author v_pwlu 2018/11/8
 */
@Slf4j
@Data
public class UserInfo implements InitializingBean, DisposableBean {
    String name;
    String email;

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
}
