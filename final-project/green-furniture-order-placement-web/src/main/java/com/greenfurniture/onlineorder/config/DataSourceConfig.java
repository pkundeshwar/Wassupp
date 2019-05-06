package com.greenfurniture.onlineorder.config;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages={"com.greenfurniture.onlineorder.jparepository"})
@EnableSpringDataWebSupport
public class DataSourceConfig {
	
	Properties properties;
	
	@PostConstruct
	public void loadProperties(){
		properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory(){
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setGenerateDdl(true);
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(adapter);
		factory.setJpaProperties(getHibernateProperties());
		factory.setPackagesToScan("com.greenfurniture.onlineorder.domain");
		factory.setDataSource(getDataSource());
		factory.afterPropertiesSet();
		
		return factory.getObject();
	}

	public Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		prop.setProperty("hibernate.show_sql", "true");
		prop.setProperty("hibernate.hbm2ddl.auto", "none");
		prop.setProperty("hibernate.cache.use_second_level_cache", "false");
		prop.setProperty("hibernate.cache.use_query_cache", "false");
		return prop;
	}

	@Bean
	public DataSource getDataSource() {
		HikariConfig dataSourceConfig = new HikariConfig("/hikari.properties");
		return new HikariDataSource(dataSourceConfig);
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
	

}
