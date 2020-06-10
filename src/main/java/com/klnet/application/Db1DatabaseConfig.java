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
@MapperScan(value="com.klnet.application.Db1AbstractDAO", sqlSessionFactoryRef="db1SqlSessionFactory")
@EnableTransactionManagement
public class Db1DatabaseConfig {
	@Bean(name="db1DataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.db1.datasource")
	public DataSource db1DataSource() {
		return DataSourceBuilder.create().build();
	}
	
	
	@Bean(name="db1SqlSessionFactory")
	@Primary
	public SqlSessionFactory db1SqlSessionFactory(@Autowired @Qualifier("db1DataSource") DataSource db1DataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(db1DataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/postgresql/*Mapper.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
//	@Bean(name="db1sqlSession")
//	@Primary
//	public SqlSessionTemplate sqlSessino(@Autowired @Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}
	
	@Bean(name ="db1SqlSessionTemplate")
	@Primary
	public SqlSessionTemplate db1SqlSessionTemplate(@Autowired @Qualifier("db1SqlSessionFactory") SqlSessionFactory db1SqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(db1SqlSessionFactory);
	}
	
	
	@Bean(name ="db1transactionManager")
	@Primary
	public DataSourceTransactionManager transactionManager(@Autowired @Qualifier("db1DataSource") DataSource db1DataSource) throws Exception {
		return new DataSourceTransactionManager(db1DataSource);
	}
	
//	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		sqlSessionFactoryBean.setDataSource(dataSource);
//
//		Resource[] arrResource = new PathMatchingResourcePatternResolver()
//				.getResources("classpath:mappers/*Mapper.xml");
//		sqlSessionFactoryBean.setMapperLocations(arrResource);
//
//		return sqlSessionFactoryBean.getObject();
//	}
//	
//	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessinoFacory){
//		return new SqlSessionTemplate(sqlSessinoFacory);
//	}

}
