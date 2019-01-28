package com.lupw.guava.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v_pwlu 2019/1/28
 */
@Configuration
@EnableConfigurationProperties(Db0DataSourceProperties.class)
@MapperScan(basePackages = "com.lupw.guava.datasource.db0.mapper", sqlSessionFactoryRef = "db0Factory")
public class Db0DataSourceConfig {

    private final Db0DataSourceProperties properties;

    @Autowired
    public Db0DataSourceConfig(Db0DataSourceProperties properties) {
        this.properties = properties;
    }


    @Bean(name = "db0Factory")
    public SqlSessionFactory getSingleDataSourceFactory(@Qualifier("db0") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db0/*.xml"));
        return sessionFactoryBean.getObject();
    }


    @Bean(name = "db0")
    public DataSource getSingleDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getDriverClass());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setInitialSize(properties.getInitialSize());
        dataSource.setMinIdle(properties.getMinIdle());
        dataSource.setMaxActive(properties.getMaxActive());
        dataSource.setMaxWait(properties.getMaxWait());
        return dataSource;
    }


    @Bean
    public MultiRoutingDataSource dataSource(@Qualifier("db0") DataSource dataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("primaryDataSource", dataSource);

        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
        // 设置数据源映射
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        // 设置默认数据源，当无法映射到数据源时会使用默认数据源
        // 默认数据源 dataSource.setDefaultTargetDataSource(primaryDataSource)
        multiRoutingDataSource.afterPropertiesSet();
        return multiRoutingDataSource;
    }
}
