package com.thread.excutor.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author v_pwlu 2018/12/10
 */
@Slf4j
public class DelegationClass implements ProxyClass {

    @Override
    public void print() {
        log.info("DelegationClass.print() 已经调用");
    }
}
