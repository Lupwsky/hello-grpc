package com.lupw.guava.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author v_pwlu 2019/1/28
 */
public class MultiRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * 见 AbstractRoutingDataSource 源码, 会自动的根据名字获取 DataSource 的 Bean, 并切换数据源
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        // 会自动的根据名字获取 DataSource 类型的的 Bean, 并切换数据源
        return MultiRoutingDataSourceContext.getCurrentDataSourceBeanName();

        // 或者直接返回 DataSource
        // return MultiRoutingDataSourceContext.getDataSource();

        // 见源码, 如果是 DataSource 对象就直接返回, 如果不是就通过 key 值去容器里获取对应的 DataSource 类型的 Bean
        // protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        //    if (dataSource instanceof DataSource) {
        //        return (DataSource)dataSource;
        //    } else if (dataSource instanceof String) {
        //        return this.dataSourceLookup.getDataSource((String)dataSource);
        //    } else {
        //        throw new IllegalArgumentException("Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
        //    }
        // }
    }
}
