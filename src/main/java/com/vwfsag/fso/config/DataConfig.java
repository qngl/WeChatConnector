package com.vwfsag.fso.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author liqiang
 */
@Configuration
@MapperScan("com.vwfsag.fso.persistence")
public class DataConfig {

	@Autowired
	private GlobalSettings settings;;

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		Properties props = settings.getProps();

		dataSource.setDriverClassName(props.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(props.getProperty("jdbc.url"));
		dataSource.setUsername(props.getProperty("jdbc.username"));
		dataSource.setPassword(props.getProperty("jdbc.password"));
		dataSource.setMaxActive(NumberUtils.toInt(props.getProperty("jdbc.maxActive"), 50));
		dataSource.setInitialSize(NumberUtils.toInt(props.getProperty("jdbc.initialPoolSize"), 2));
		dataSource.setDefaultAutoCommit(false);
		dataSource.setValidationQuery("select 1");

		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setTypeAliasesPackage("com.vwfsag.fso.domain");
		return sessionFactory;
	}
}
