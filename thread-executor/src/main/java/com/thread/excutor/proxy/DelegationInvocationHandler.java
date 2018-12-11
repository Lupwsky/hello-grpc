package com.thread.excutor.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类对应的调用委托类的调用处理程序类
 *
 * @author v_pwlu 2018/12/10
 */
@Slf4j
public class DelegationInvocationHandler implements InvocationHandler {

    /**
     * 持有的委托类的一个对象
     */
    private DelegationClass delegationClass;


    public DelegationInvocationHandler(DelegationClass delegationClass) {
        this.delegationClass = delegationClass;
    }


    /**
     * @param proxy 被代理的对象
     * @param method 要调用的方法
     * @param args 方法调用时所需要的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("DelegationClass.print() 开始调用");
        method.invoke(delegationClass, args);
        log.info("DelegationClass.print() 结束调用");
        return delegationClass;
    }
}
