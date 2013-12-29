package com.tmt.kontroll.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.commons.config.CommonsConfig;


@Configuration
@ComponentScan(value = {"com.tmt.kontroll.web"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Import(value = {CommonsConfig.class})
public class WebConfig {

}
