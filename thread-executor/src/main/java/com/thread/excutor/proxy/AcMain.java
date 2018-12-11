package com.thread.excutor.proxy;

import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @author v_pwlu 2018/12/10
 */
public class AcMain {

    public static void main(String[] args) {
//        DelegationClass delegationClass = new DelegationClass();
//        DelegationInvocationHandler handler = new DelegationInvocationHandler(delegationClass);
//        ProxyClass proxyClass = (ProxyClass) Proxy.newProxyInstance(delegationClass.getClass().getClassLoader(),
//                delegationClass.getClass().getInterfaces(), handler);
//        proxyClass.print();

        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(DelegationClass.class);
        enhancer.setCallback(new ProxyInterceptor());
        DelegationClass delegationClass =(DelegationClass)enhancer.create();
        delegationClass.print();
    }
}
