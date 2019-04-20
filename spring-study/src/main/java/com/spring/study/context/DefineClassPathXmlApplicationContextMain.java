package com.spring.study.context;

import com.spring.study.beans.*;
import lombok.extern.slf4j.Slf4j;


/**
 * @author v_pwlu 2019/3/25
 */
@Slf4j
public class DefineClassPathXmlApplicationContextMain {

    public static void main(String[] args) {
        DefineClassPathXmlApplicationContext applicationContext = new DefineClassPathXmlApplicationContext("beans/UserBeans.xml");
        UserInfo userInfo = applicationContext.getBean("userInfo", UserInfo.class);
        log.info("name = {}, email = {}", userInfo.getName(), userInfo.getEmail());

        UserInfoA userInfoA = applicationContext.getBean("userInfoA", UserInfoA.class);
        log.info("type = {}", userInfoA.getUserInfoA());

        UserInfoB userInfoB = applicationContext.getBean("userInfoB", UserInfoB.class);
        log.info("type = {}", userInfoB.getUserInfoB());

        // "&userInfoA" 或者 "&userInfoB" 可以获取到 FactoryBean 本身这个 Bean
        BeanFactoryBean beanFactoryBean = applicationContext.getBean("&userInfoA", BeanFactoryBean.class);
        try {
            // 通过工厂 Bean 创建一个 UserInfoA 的实例
            beanFactoryBean.setUserInfoType("userInfoA");
            UserInfoA userInfoA1 = (UserInfoA) beanFactoryBean.getObject();
            if (userInfoA1 != null) {
                log.info("type = {}", userInfoA1.getUserInfoA());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 枚举测试
        log.info("{}", EnumTest.getInstance("白色").getIndex());
        log.info("{}", EnumTest.getInstance("白色").getName());
        log.info("{}", EnumTest.getInstance("白色").getDesc());
    }
}
