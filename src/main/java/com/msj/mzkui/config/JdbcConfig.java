package com.msj.mzkui.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


@Configuration
@EnableTransactionManagement
public class JdbcConfig implements TransactionManagementConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(JdbcConfig.class);
    //private static final Logger logger = Logger.getLogger(JdbcConfig.class);

    @Autowired
    private DataSourceProperties properties;

    @Bean
    public DruidDataSource getDataSource() {
        logger.debug("DruidDataSource开始连接数据源...");
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(this.properties.getDriverClass());
        ds.setUrl(this.properties.getUrl());
        ds.setUsername(this.properties.getUsername());
        ds.setPassword(this.properties.getPassword());
        ds.setMaxActive(this.properties.getMaxActive());
        ds.setMaxWait(this.properties.getMaxWait());
        ds.setInitialSize(this.properties.getInitialSize());
        ds.setValidationQuery(this.properties.getValidationQuery());
        ds.setPoolPreparedStatements(this.properties.isPoolPreparedStatements());
        ds.setMaxPoolPreparedStatementPerConnectionSize(this.properties.getMaxPoolPreparedStatementPerConnectionSize());
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        return ds;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }
}
