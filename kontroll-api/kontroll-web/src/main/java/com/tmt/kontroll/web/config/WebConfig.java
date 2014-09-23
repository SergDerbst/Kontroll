package com.tmt.kontroll.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.tmt.kontroll.commons.config.CommonsConfig;


@Configuration
@ComponentScan(value = {"com.tmt.kontroll.web"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@EnableWebMvc
@Import(value = {CommonsConfig.class})
public class WebConfig {

	@Bean
	public UrlBasedViewResolver urlBasedViewResolver() {
		final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
}
