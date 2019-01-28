package com.lupw.guava.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author v_pwlu 2019/1/28
 */
@Slf4j
public class MultiRoutingDataSourceContext {

    private final static ThreadLocal<String> DATA_SOURCE_BEAN_NAME = new ThreadLocal<>();

    private MultiRoutingDataSourceContext() {
    }


    public static String getCurrentDataSourceBeanName() {
        String tenant = DATA_SOURCE_BEAN_NAME.get();
        if (StringUtils.isEmpty(tenant)) {
            log.error("dataSourceName 为空 ");
        }
        return tenant;
    }


    public static void setCurrentDataSourceBeanName(String tenant) {
        DATA_SOURCE_BEAN_NAME.set(tenant);
    }


    public static void clear() {
        DATA_SOURCE_BEAN_NAME.remove();
    }


    public static DataSource getDataSource() {
        String currentDataSourceBeanName = getCurrentDataSourceBeanName();
        return ApplicationContextUtil.context.getBean(currentDataSourceBeanName, DataSource.class);
    }
}
