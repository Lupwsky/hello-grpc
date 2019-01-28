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

/**
 * @author v_pwlu 2019/1/28
 */
@Configuration
@EnableConfigurationProperties(Db1DataSourceProperties.class)
@MapperScan(basePackages = "com.lupw.guava.datasource.db1.mapper", sqlSessionFactoryRef = "db1Factory")
public class Db1DataSourceConfig {

    private final Db0DataSourceProperties properties;

    @Autowired
    public Db1DataSourceConfig(Db0DataSourceProperties properties) {
        this.properties = properties;
    }


    @Bean(name = "db1Factory")
    public SqlSessionFactory getSingleDataSourceFactory(@Qualifier("db1") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db1/*.xml"));
        return sessionFactoryBean.getObject();
    }


    @Bean(name = "db1")
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
}
