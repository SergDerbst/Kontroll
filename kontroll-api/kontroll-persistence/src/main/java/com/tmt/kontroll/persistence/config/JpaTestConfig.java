package com.tmt.kontroll.persistence.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(name = "database", value = "classpath:/jpaConfig/kontrollJpaConfig.properties")
public class JpaTestConfig extends JpaConfig {

	@Value("${database.driverClassName}")
	private String	driverClassName;

	@Value("${database.url}")
	private String	url;

	@Value("${database.user}")
	private String	username;

	@Value("${database.password}")
	private String	password;

	@Override
	@Bean
	public DataSource dataSource() {
		final BasicDataSource basicDataSouce = new BasicDataSource();
		basicDataSouce.setDriverClassName(this.driverClassName);
		basicDataSouce.setUrl(this.url);
		basicDataSouce.setUsername(this.username);
		basicDataSouce.setPassword(this.password);

		return basicDataSouce;
	}
}
