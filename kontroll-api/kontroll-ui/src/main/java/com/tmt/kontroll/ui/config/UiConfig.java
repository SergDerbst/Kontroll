package com.tmt.kontroll.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.commons.config.CommonsConfig;
import com.tmt.kontroll.content.config.ContentConfig;
import com.tmt.kontroll.context.config.ContextConfig;
import com.tmt.kontroll.security.config.SecurityConfig;

@Configuration
@Import({CommonsConfig.class, ContextConfig.class, SecurityConfig.class, ContentConfig.class})
@ComponentScan(value = {"com.tmt.kontroll.ui"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class UiConfig {

	@Bean
	public UiProperties uiProperties() {
		final UiProperties uiProperties = new UiProperties();
		uiProperties.basePackages().add("com.tmt.kontroll.ui");
		return uiProperties;
	}
}
