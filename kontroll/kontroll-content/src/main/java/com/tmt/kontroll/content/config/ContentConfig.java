package com.tmt.kontroll.content.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.content"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class ContentConfig {
	
	@Bean
	public ContentProperties contentProperties() {
		final ContentProperties contentProperties = new ContentProperties();
		contentProperties.getContentItemBasePackages().add("com.tmt.kontroll.content");
		return contentProperties;
	}
}
