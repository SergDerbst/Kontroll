package com.tmt.kontroll.content.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.content"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class ContentTestConfig extends ContentConfig {

}
