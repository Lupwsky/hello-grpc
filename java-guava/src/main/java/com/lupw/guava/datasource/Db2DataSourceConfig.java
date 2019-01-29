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
@MapperScan(basePackages = "com.lupw.guava.datasource.db2.mapper", sqlSessionFactoryRef = "db2Factory")
public class Db2DataSourceConfig {

    @Bean(name = "db2Factory")
    public SqlSessionFactory getDataSourceFactory(@Qualifier("db2") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:db2/*.xml"));
        return sessionFactoryBean.getObject();
    }


    @Bean(name = "db2")
    public DataSource getDataSource(Db2DataSourceProperties db2DataSourceProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(db2DataSourceProperties.getDriverClass());
        dataSource.setUrl(db2DataSourceProperties.getUrl());
        dataSource.setUsername(db2DataSourceProperties.getUsername());
        dataSource.setPassword(db2DataSourceProperties.getPassword());
        dataSource.setInitialSize(db2DataSourceProperties.getInitialSize());
        dataSource.setMinIdle(db2DataSourceProperties.getMinIdle());
        dataSource.setMaxActive(db2DataSourceProperties.getMaxActive());
        dataSource.setMaxWait(db2DataSourceProperties.getMaxWait());
        return dataSource;
    }
}
