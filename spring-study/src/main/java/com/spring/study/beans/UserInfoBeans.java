package com.spring.study.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author v_pwlu 2019/4/11
 */
@Component
public class UserInfoBeans {

    @Bean
    @MyAnnotation(value = "hello")
    public UserInfo userInfo() {
        return UserInfo.builder().build();
    }

    @Bean
    public UserInfoA userInfoA() {
        return UserInfoA.builder().build();
    }

    @Bean
    @Lazy(value = false)
    public UserInfoB userInfoB() {
        return UserInfoB.builder().build();
    }
}
