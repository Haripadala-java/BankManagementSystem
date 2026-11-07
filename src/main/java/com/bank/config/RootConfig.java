package com.bank.config;


import java.util.Properties;

import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.bank.repository")
@EnableTransactionManagement
public class RootConfig {
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/bank_management");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entitymanager() {
		
		LocalContainerEntityManagerFactoryBean cmf = new LocalContainerEntityManagerFactoryBean();
		cmf.setDataSource(dataSource());
		cmf.setPackagesToScan("com.bank.entity");
		cmf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties pro = new Properties();
		 pro.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	     pro.put("hibernate.hbm2ddl.auto", "update");
	     pro.put("hibernate.show_sql", "true");
	     pro.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
	     
	     cmf.setJpaProperties(pro);
	     return cmf;
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager(entitymanager().getObject()); 
	}
}
