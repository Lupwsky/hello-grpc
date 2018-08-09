package com.lupw.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/8/9
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:dubbo-consumer.xml"});
        context.start();
        // 获取远程服务代理
        UserCenterService userCenterService = (UserCenterService) context.getBean("userCenterService");
        // 执行远程方法
        String username = userCenterService.getUsername();
        System.out.println(username);
    }
}
