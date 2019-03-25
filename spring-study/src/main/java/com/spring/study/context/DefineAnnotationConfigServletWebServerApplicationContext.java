package com.spring.study.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author v_pwlu 2019/3/25
 */
@Slf4j
public class DefineAnnotationConfigServletWebServerApplicationContext extends AnnotationConfigServletWebServerApplicationContext {

    @Override
    protected void initPropertySources() {
        // 可以对不同环境下的属进行检测或者设置不同环境下所需属性
        // 观察日志, 可以看到 initPropertySources 方法被执行了两次, 是因为除了 AbstractApplicationContext 类中有调用
        // 在 ServletWebServerApplicationContext 类的 onRefresh 中执行 createWebServer 方法时也会调用一次 initPropertySources 方法
        log.info("[系统配置属性检测] 项目启动, 开始检测");
        String[] activeProfiles = this.getEnvironment().getActiveProfiles();
        if (Arrays.stream(activeProfiles).allMatch(activeProfile -> Objects.equals("dev", activeProfile))) {
            log.info("[系统配置属性检测] 当前是 DEV 环境, 对 JAVA_HOME 属性进行检测");
            this.getEnvironment().setRequiredProperties("JAVA_HOME");
        } else {
            log.info("[系统配置属性检测] 当前非 DEV 环境, 对 JAVA_HOME1 属性进行检测");
            this.getEnvironment().setRequiredProperties("JAVA_HOME1");
        }
        log.info("[系统配置属性检测] 通过检测");
    }
}
