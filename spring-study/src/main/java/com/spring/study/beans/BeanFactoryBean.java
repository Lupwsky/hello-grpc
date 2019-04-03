package com.spring.study.beans;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author v_pwlu 2019/4/3
 */
@Data
public class BeanFactoryBean implements FactoryBean {

    private String userInfoType;

    // BeanFactory 是一个接口, 他定义了 Spring 容器里面对 Bean 最基础的操作, 例如从获取 Bean, 判断一个 Bean 是否在容器中存在, 所有的容器要实现这个接口
    // FactoryBean 是一个接口, 普通的 Bean 如果实现这个接口可以变成一个特殊的 Bean (工厂 Bean, 可以让容器管理), 他可以为普通的 Bean 提供了一个简单工厂模式, 通过配置创建不同的对象
    // 实现 FactoryBean 接口的 Bean 需要实现三个接口, 分别如下

    // 返回生成的对象

    @Override
    public Object getObject() throws Exception {
        switch (userInfoType) {
            case "userInfoA": return UserInfoA.builder().userInfoA("A").build();
            case "userInfoB": return UserInfoB.builder().userInfoB("B").build();
            default: throw new RuntimeException("不存在此类型的 Bean");
        }
    }

    // 返回生成对象的类型

    @Override
    public Class<?> getObjectType() {
        switch (userInfoType) {
            case "userInfoA": return UserInfoA.class;
            case "userInfoB": return UserInfoB.class;
            default: throw new RuntimeException("不存在此类型的 Bean 的类型");
        }
    }

    // 生成的对象是否是单例的, true = 是, false = 否

    @Override
    public boolean isSingleton() {
        return true;
    }
}
