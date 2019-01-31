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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v_pwlu 2019/1/28
 */
// 多个 basePackages = {"", ""}
@Configuration
@EnableConfigurationProperties({Db0DataSourceProperties.class, Db1DataSourceProperties.class, Db2DataSourceProperties.class})
@MapperScan(basePackages = "com.lupw.guava.datasource.db.mapper", sqlSessionFactoryRef = "dbFactory")
public class MultiDataSourceConfig {

    private final Db0DataSourceProperties db0DataSourceProperties;
    private final Db1DataSourceProperties db1DataSourceProperties;
    private final Db2DataSourceProperties db2DataSourceProperties;

    @Autowired
    public MultiDataSourceConfig(Db0DataSourceProperties db0DataSourceProperties,
                                 Db1DataSourceProperties db1DataSourceProperties,
                                 Db2DataSourceProperties db2DataSourceProperties) {
        this.db0DataSourceProperties = db0DataSourceProperties;
        this.db1DataSourceProperties = db1DataSourceProperties;
        this.db2DataSourceProperties = db2DataSourceProperties;
    }

    @Bean(name = "db0")
    public DataSource getDb0DataSource() {
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


    @Bean(name = "db1")
    public DataSource getDb1DataSource() {
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


    @Bean(name = "db2")
    public DataSource getDb2DataSource() {
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


    @Bean(name = "multiRoutingDataSource")
    public DataSource getMultiRoutingDataSource() {
        MultiRoutingDataSource dataSource = new MultiRoutingDataSource();
        dataSource.setDefaultTargetDataSource(getDb0DataSource());
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("db0", getDb0DataSource());
        dataSourceMap.put("db1", getDb1DataSource());
        dataSourceMap.put("db2", getDb2DataSource());
        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }


    @Bean(name = "dbFactory")
    public SqlSessionFactory getDataSourceFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(getMultiRoutingDataSource());
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*/*.xml"));
        return sessionFactoryBean.getObject();
    }


    @Bean(name = "multiRoutingTransaction")
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(getMultiRoutingDataSource());
    }
}
