package com.tmt.kontroll.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.ui.config.UiConfig;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.web"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Import({UiConfig.class})
public class WebConfig {

}
