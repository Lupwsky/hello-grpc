package com.spring.study;

import com.alibaba.fastjson.JSONObject;
import com.spring.study.beans.UserInfo;
import com.spring.study.beans.UserInfoA;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author v_pwlu 2019/3/25
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("/beans/UserBeans.xml"));
        UserInfo userInfo = (UserInfo) beanFactory.getBean("userInfo", "lupengwei", "lupengwei@qq.com");
        log.info("{}", JSONObject.toJSONString(userInfo));
//
//        UserInfoA userInfoA = new UserInfoA();
//        beanFactory.registerSingleton("userInfoA", userInfoA);
//        log.info("{}", Arrays.toString(beanFactory.getSingletonNames()));
//
//        Object object = beanFactory.getSingletonMutex();
        System.exit(0);
    }
}
