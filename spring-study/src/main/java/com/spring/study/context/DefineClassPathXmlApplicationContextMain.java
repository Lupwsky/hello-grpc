package com.spring.study.context;

import com.spring.study.beans.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Properties;

import static org.springframework.context.ConfigurableApplicationContext.*;

/**
 * @author v_pwlu 2019/3/25
 */
@Slf4j
public class DefineClassPathXmlApplicationContextMain {

    public static void main(String[] args) {
        DefineClassPathXmlApplicationContext applicationContext = new DefineClassPathXmlApplicationContext("beans/UserBeans.xml");
        UserInfo userInfo = applicationContext.getBean("userInfo", UserInfo.class);
        log.info("name = {}, email = {}", userInfo.getName(), userInfo.getEmail());

        ConfigurableEnvironment environment1 = applicationContext.getBean(ENVIRONMENT_BEAN_NAME, ConfigurableEnvironment.class);
        ConfigurableEnvironment environment2 = applicationContext.getEnvironment();

        // 输出示例: key =user.home, value = /Users/lupengwei
        Properties systemProperties = applicationContext.getBean(SYSTEM_PROPERTIES_BEAN_NAME, Properties.class);
        log.info("-----------------------------------------------------------------------");
        systemProperties.forEach((key, value) -> log.info("key = {}, value = {}", key, value));

        // 输出示例: key = JAVA_HOME, value = /Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home
        Properties systemEnvironmentProperties = applicationContext.getBean(SYSTEM_ENVIRONMENT_BEAN_NAME, Properties.class);
        log.info("-----------------------------------------------------------------------");
        systemEnvironmentProperties.forEach((key, value) -> log.info("key = {}, value = {}", key, value));

        log.info("{}", userInfo.getApplicationContext().equals(applicationContext));
    }
}
