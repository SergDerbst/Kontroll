package com.tmt.kontroll.context.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.commons.config.CommonsConfig;

@Configuration
@Import({CommonsConfig.class})
@ComponentScan(value = {"com.tmt.kontroll.context"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class ContextConfig {

	@Bean
	public ContextProperties contextProperties() {
		final ContextProperties contextProperties = new ContextProperties();
		contextProperties.contextServiceBasePackages().add("com.tmt.kontroll.context");
		return contextProperties;
	}
}
