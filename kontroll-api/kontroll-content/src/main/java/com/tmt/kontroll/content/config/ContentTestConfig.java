package com.tmt.kontroll.content.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.tmt.kontroll.context.config.ContextConfig;
import com.tmt.kontroll.persistence.config.PersistenceTestConfig;
import com.tmt.kontroll.test.config.TestConfig;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.content"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Import({TestConfig.class, ContextConfig.class, PersistenceTestConfig.class})
@EnableJpaRepositories(basePackages = {"com.tmt.kontroll.content.persistence.repositories"}, entityManagerFactoryRef = "entityManagerFactoryBean", transactionManagerRef = "transactionManager")
public class ContentTestConfig {

	@Bean
	public ContentProperties contentProperties() {
		final ContentProperties contentProperties = new ContentProperties();
		contentProperties.contentItemBasePackages().add("com.tmt.kontroll.content");
		return contentProperties;
	}
}