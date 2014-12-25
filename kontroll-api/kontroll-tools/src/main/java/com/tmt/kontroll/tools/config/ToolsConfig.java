package com.tmt.kontroll.tools.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.ui.config.UiConfig;

@Configuration
@Import({UiConfig.class})
@ComponentScan(value = {"com.tmt.kontroll.tools"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class ToolsConfig {

}
