package com.tmt.kontroll.content.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.tmt.kontroll.business.config.BusinessConfig;
import com.tmt.kontroll.context.config.ContextConfig;
import com.tmt.kontroll.persistence.config.PersistenceConfig;

@Configuration
@Import({BusinessConfig.class, ContextConfig.class, PersistenceConfig.class})
@ComponentScan(value = {"com.tmt.kontroll.content"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@EnableJpaRepositories(basePackages = {"com.tmt.kontroll.content.persistence.repositories"}, entityManagerFactoryRef = "entityManagerFactoryBean", transactionManagerRef = "transactionManager")
@EnableAspectJAutoProxy
public class ContentConfig {

	@Bean
	public ContentProperties contentProperties() {
		return new ContentProperties();
	}
}
