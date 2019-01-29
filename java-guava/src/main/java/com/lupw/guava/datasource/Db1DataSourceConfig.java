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
@MapperScan(basePackages = "com.lupw.guava.datasource.db1.mapper", sqlSessionFactoryRef = "db1Factory")
public class Db1DataSourceConfig {

    @Bean(name = "db1Factory")
    public SqlSessionFactory getDataSourceFactory(@Qualifier("db1") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:db1/*.xml"));
        return sessionFactoryBean.getObject();
    }


    @Bean(name = "db1")
    public DataSource getDataSource(Db1DataSourceProperties db1DataSourceProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(db1DataSourceProperties.getDriverClass());
        dataSource.setUrl(db1DataSourceProperties.getUrl());
        dataSource.setUsername(db1DataSourceProperties.getUsername());
        dataSource.setPassword(db1DataSourceProperties.getPassword());
        dataSource.setInitialSize(db1DataSourceProperties.getInitialSize());
        dataSource.setMinIdle(db1DataSourceProperties.getMinIdle());
        dataSource.setMaxActive(db1DataSourceProperties.getMaxActive());
        dataSource.setMaxWait(db1DataSourceProperties.getMaxWait());
        return dataSource;
    }
}
