package com.uitgis.kras.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("oracle")
@Configuration
@MapperScan(value="com.uitgis.kras.mapper", sqlSessionFactoryRef="oracleSqlSessionFactory")
@Import({ TransactionConfig.class })
@EnableTransactionManagement
public class OracleDbConfig {

	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(oracleDataSource());
	}

	@Bean(name="oracleDataSource")
	@Primary
	@ConfigurationProperties(prefix="database.datasource")
	public DataSource oracleDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="oracleSqlSessionFactory")
	@Primary
	public SqlSessionFactory oracleSqlSessionFactory(@Qualifier("oracleDataSource") DataSource oracleDataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(oracleDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/oracle/*.xml"));
        return sqlSessionFactoryBean.getObject();
	}
	
}
