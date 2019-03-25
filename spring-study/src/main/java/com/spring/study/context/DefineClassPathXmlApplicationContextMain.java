package com.spring.study.context;

import com.spring.study.beans.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author v_pwlu 2019/3/25
 */
@Slf4j
public class DefineClassPathXmlApplicationContextMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new DefineClassPathXmlApplicationContext("beans/UserBeans.xml");
        UserInfo userInfo = applicationContext.getBean("userInfo", UserInfo.class);
        log.info("name = {}, email = {}", userInfo.getName(), userInfo.getEmail());
    }
}
