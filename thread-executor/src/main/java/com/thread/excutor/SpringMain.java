package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.Constants;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.net.*;
import java.util.*;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/9/19
 */
@Slf4j
public class SpringMain {

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("beans/UserBeans.xml");
//        UserInfo userInfo = (UserInfo) context.getBean("userInfo");

        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beans/UserBeans.xml"));
        UserInfo userInfo = (UserInfo) beanFactory.getBean("userInfo");
        log.info("[测试1] result1 = {}", userInfo);

        Constants constants = new Constants(XmlBeanDefinitionReader.class);
        log.info(constants.getClassName());
    }
}