package com.tmt.kontroll.context.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.context"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class ContextConfig {

	@Bean
	public ContextProperties contextProperties() {
		final ContextProperties contextProperties = new ContextProperties();
		contextProperties.contextServiceBasePackages().add("com.tmt.kontroll.context");
		return contextProperties;
	}
}
