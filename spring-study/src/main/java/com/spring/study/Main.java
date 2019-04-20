package com.spring.study;

import com.alibaba.fastjson.JSONObject;
import com.spring.study.beans.UserInfo;
import com.spring.study.beans.UserInfoA;
import com.spring.study.beans.UserInfoB;
import lombok.extern.slf4j.Slf4j;

import org.javatuples.Pair;
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

        Pair<UserInfoA, UserInfoB> userInfoBPair = getUserInfoBPair();
        UserInfoA userInfoA = userInfoBPair.getValue0();
        UserInfoB userInfoB = userInfoBPair.getValue1();
        log.info("userInfoA = {}, userInfoB = {}", JSONObject.toJSONString(userInfoA), JSONObject.toJSONString(userInfoB));
    }


    /**
     * https://www.javatuples.org/
     */
    private static Pair<UserInfoA, UserInfoB> getUserInfoBPair() {
        return Pair.with(UserInfoA.builder().userInfoA("A").build(),
                UserInfoB.builder().userInfoB("B").build());
    }
}
