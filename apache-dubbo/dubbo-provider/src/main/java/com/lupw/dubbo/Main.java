package com.lupw.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/8/9
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:dubbo-provider.xml"});
        context.start();
        System.in.read();
        System.out.println("程序已经结束!");
    }
}
