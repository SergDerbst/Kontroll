package com.tmt.kontroll.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.tools.config.ToolsConfig;

@Configuration
@Import({ToolsConfig.class})
@ComponentScan(value = {"com.tmt.kontroll.web"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class WebConfig {

}
