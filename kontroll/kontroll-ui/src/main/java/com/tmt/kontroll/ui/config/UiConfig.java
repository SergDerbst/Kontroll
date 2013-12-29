package com.tmt.kontroll.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.ui"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class UiConfig {

	@Bean
	public UiProperties uiProperties() {
		final UiProperties uiProperties = new UiProperties();
		uiProperties.getLayoutBasePackages().add("com.tmt.kontroll.ui");
		return uiProperties;
	}
}
