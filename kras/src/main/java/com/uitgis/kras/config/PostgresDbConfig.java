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

@Profile("postgres")
@Configuration
@MapperScan(value="com.uitgis.kras.mapper", sqlSessionFactoryRef="postgresSqlSessionFactory")
@Import({ TransactionConfig.class })
@EnableTransactionManagement
public class PostgresDbConfig {

	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(postgresDataSource());
	}

	@Bean(name="postgresDataSource")
	@Primary
	@ConfigurationProperties(prefix="database.datasource")
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="postgresSqlSessionFactory")
	@Primary
	public SqlSessionFactory postgresSqlSessionFactory(@Qualifier("postgresDataSource") DataSource postgresDataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(postgresDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/postgres/*.xml"));
        return sqlSessionFactoryBean.getObject();
	}
	
}
