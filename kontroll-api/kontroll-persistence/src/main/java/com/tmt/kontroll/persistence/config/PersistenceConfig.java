package com.tmt.kontroll.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;



/**
 * Configuration class to set up the Spring persistence context.
 *
 * @author Sergio Weigel
 *
 */
@Configuration
@Import({GeneralContextConfig.class, Jsr303BeanValidationConfig.class})
@ComponentScan(value = {"com.tmt.kontroll.persistence"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class PersistenceConfig {

	@Bean
	public PersistenceProperties persistenceProperties() {
		final PersistenceProperties persistenceProperties = new PersistenceProperties();
		return persistenceProperties;
	}
}
