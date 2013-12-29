package com.tmt.kontroll.persistence.commons.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tmt.kontroll.persistence.config.JpaConfig;

@Configuration
@EnableTransactionManagement
@PropertySource(name = "database", value = "classpath:jpaConfig.properties")
@ImportResource(value = {"classpath:/jpaConfig/jpaConfig.xml"})
public class JpaConfigTest extends JpaConfig {

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
		basicDataSouce.setDriverClassName(driverClassName);
		basicDataSouce.setUrl(url);
		basicDataSouce.setUsername(username);
		basicDataSouce.setPassword(password);

		return basicDataSouce;
	}
}
