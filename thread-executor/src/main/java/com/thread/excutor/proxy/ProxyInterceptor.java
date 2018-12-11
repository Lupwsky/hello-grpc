package com.thread.excutor.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author v_pwlu 2018/12/11
 */
@Slf4j
public class ProxyInterceptor implements MethodInterceptor {

    /**
     * 重写拦截方法, 在代理类方法中加入业务
     *
     * @param o           目标对象
     * @param method      目标方法
     * @param objects     为参数
     * @param methodProxy CGlib方法代理对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("DelegationClass.print() 开始调用");
        // 调用代理类实例上的父类方法
        Object result = methodProxy.invokeSuper(o, objects);
        log.info("DelegationClass.print() 结束调用");
        return result;
    }
}
