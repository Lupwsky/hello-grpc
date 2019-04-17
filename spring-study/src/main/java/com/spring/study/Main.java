package com.spring.study;

import com.alibaba.fastjson.JSONObject;
import com.spring.study.beans.UserInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;


/**
 * @author v_pwlu 2019/3/25
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("/beans/UserBeans.xml"));
        UserInfo userInfo = (UserInfo) beanFactory.getBean("userInfo", "lupengwei", "lupengwei@qq.com");
        log.info("{}", JSONObject.toJSONString(userInfo));
        System.exit(0);
    }
}
