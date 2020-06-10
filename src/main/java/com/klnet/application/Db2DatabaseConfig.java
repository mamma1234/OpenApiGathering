package com.klnet.application;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication

@Configuration
@MapperScan(value="com.klnet.application.Db2AbstractDAO", sqlSessionFactoryRef="db2SqlSessionFactory")
@EnableTransactionManagement
public class Db2DatabaseConfig {
	@Bean(name="db2DataSource")
	@ConfigurationProperties(prefix="spring.db2.datasource")
	public DataSource db2DataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="db2SqlSessionFactory")
	public SqlSessionFactory db1SqlSessionFactory(@Autowired @Qualifier("db2DataSource") DataSource db2DataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(db2DataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/oracle/*Mapper.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name ="db2SqlSessionTemplate")
	public SqlSessionTemplate db1SqlSessionTemplate(@Autowired @Qualifier("db2SqlSessionFactory") SqlSessionFactory db2SqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(db2SqlSessionFactory);
	}

	
	@Bean(name ="db2transactionManager")
	public DataSourceTransactionManager transactionManager(@Autowired @Qualifier("db2DataSource") DataSource db2DataSource) throws Exception {
		return new DataSourceTransactionManager(db2DataSource);
	}

}
