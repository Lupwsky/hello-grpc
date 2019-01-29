package com.lupw.guava.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v_pwlu 2019/1/28
 */
@Configuration
@MapperScan(basePackages = Db0DataSourceConfig.MAPPER_SCAN_PACKAGE,
        sqlSessionFactoryRef = Db0DataSourceConfig.DB0_SQL_SESSION_FACTORY_BEAN_NAME)
public class Db0DataSourceConfig {

    private final static String DB0_BEAN_NAME = "db0";
    private final static String DB0_TRANSACTION_MANAGER_BEAN_NAME = "db0TransactionManager";
    private final static String DB0_MAPPER_LOCATIONS = "classpath:db0/*.xml";
    final static String DB0_SQL_SESSION_FACTORY_BEAN_NAME = "db0Factory";
    final static String MAPPER_SCAN_PACKAGE = "com.lupw.guava.datasource.db0.mapper";

    @Primary
    @Bean(name = DB0_BEAN_NAME)
    public DataSource getDataSource(Db0DataSourceProperties db0DataSourceProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(db0DataSourceProperties.getDriverClass());
        dataSource.setUrl(db0DataSourceProperties.getUrl());
        dataSource.setUsername(db0DataSourceProperties.getUsername());
        dataSource.setPassword(db0DataSourceProperties.getPassword());
        dataSource.setInitialSize(db0DataSourceProperties.getInitialSize());
        dataSource.setMinIdle(db0DataSourceProperties.getMinIdle());
        dataSource.setMaxActive(db0DataSourceProperties.getMaxActive());
        dataSource.setMaxWait(db0DataSourceProperties.getMaxWait());
        return dataSource;
    }


    @Primary
    @Bean(name = DB0_SQL_SESSION_FACTORY_BEAN_NAME)
    public SqlSessionFactory getDataSourceFactory(@Qualifier(DB0_BEAN_NAME) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DB0_MAPPER_LOCATIONS));
        return sessionFactoryBean.getObject();
    }


    @Primary
    @Bean(name = DB0_TRANSACTION_MANAGER_BEAN_NAME)
    public DataSourceTransactionManager getTransactionManager(@Qualifier(DB0_BEAN_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean
//    public MultiRoutingDataSource dataSource(@Qualifier("db0") DataSource dataSource) {
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put("primaryDataSource", dataSource);
//
//        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
//        // 设置数据源映射
//        multiRoutingDataSource.setTargetDataSources(targetDataSources);
//        // 设置默认数据源，当无法映射到数据源时会使用默认数据源
//        // 默认数据源 dataSource.setDefaultTargetDataSource(primaryDataSource)
//        multiRoutingDataSource.afterPropertiesSet();
//        return multiRoutingDataSource;
//    }
}
