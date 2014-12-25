package com.tmt.kontroll.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.commons.config.CommonsConfig;

@Configuration
@Import(CommonsConfig.class)
@ComponentScan(value = {"com.tmt.kontroll.business"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@EnableAspectJAutoProxy
public class BusinessConfig {

	@Bean
	public BusinessProperties businessProperties() {
		return new BusinessProperties();
	}
}
