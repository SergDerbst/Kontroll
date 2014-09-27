package com.tmt.kontroll.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.content.config.ContentConfig;
import com.tmt.kontroll.context.config.ContextConfig;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.ui"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Import({ContextConfig.class, ContentConfig.class})
public class UiConfig {

	@Bean
	public UiProperties uiProperties() {
		final UiProperties uiProperties = new UiProperties();
		uiProperties.layoutBasePackages().add("com.tmt.kontroll.ui");
		return uiProperties;
	}
}
