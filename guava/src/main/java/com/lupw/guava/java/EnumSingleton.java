package com.lupw.guava.java;

/**
 * @author v_pwlu 2019/5/23
 */
public enum  EnumSingleton {

    // 实例被保证只会被实例化一次
    INSTANCE;

    private Singleton singleton;

    EnumSingleton() {
        singleton = new Singleton();
    }

    public Singleton getInstance() {
        return singleton;
    }
}
