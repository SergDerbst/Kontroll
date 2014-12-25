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
		return new ContextProperties();
	}
}
