package com.tmt.kontroll.persistence.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(name = "database", value = "classpath:/jpaConfig/kontrollJpaConfig.properties")
@EnableJpaRepositories(basePackages = {"com.tmt.kontroll.persistence.repositories", "com.tmt.toomanythoughts.persistence.repositories"}, entityManagerFactoryRef = "entityManagerFactoryBean", transactionManagerRef = "transactionManager")
public class KontrollTestJpaConfig {

	@Value("${database.driverClassName}")
	private String	driverClassName;

	@Value("${database.url}")
	private String	url;

	@Value("${database.user}")
	private String	username;

	@Value("${database.password}")
	private String	password;

	@Value("${database.hibernate.hbm2ddl.auto}")
	protected String	hbm2ddlAuto;

	@Value("${database.hibernate.dialect}")
	protected String	hibernateDialect;

	@Bean
	public DataSource dataSource() {
		final BasicDataSource basicDataSouce = new BasicDataSource();
		basicDataSouce.setDriverClassName(this.driverClassName);
		basicDataSouce.setUrl(this.url);
		basicDataSouce.setUsername(this.username);
		basicDataSouce.setPassword(this.password);

		return basicDataSouce;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws IllegalArgumentException, NamingException {
		final LocalContainerEntityManagerFactoryBean localSessionFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localSessionFactoryBean.setPackagesToScan("com.tmt.kontroll.persistence.entities", "com.tmt.kontroll.content.persistence.entities");
		localSessionFactoryBean.setDataSource(this.dataSource());
		localSessionFactoryBean.setJpaVendorAdapter(this.hibernateVendor());

		final Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", this.hbm2ddlAuto);
		jpaProperties.setProperty("hibernate.dialect", this.hibernateDialect);
		jpaProperties.setProperty("hibernate.show_sql", Boolean.TRUE.toString());
		localSessionFactoryBean.setJpaProperties(jpaProperties);

		return localSessionFactoryBean;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		final PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor = new PersistenceExceptionTranslationPostProcessor();
		return persistenceExceptionTranslationPostProcessor;
	}

	@Bean
	public HibernateJpaVendorAdapter hibernateVendor() {
		final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public JpaTransactionManager transactionManager() throws IllegalArgumentException, NamingException {
		final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(this.entityManagerFactoryBean().getObject());
		return jpaTransactionManager;
	}
}
