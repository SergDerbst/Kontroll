package com.tmt.kontroll.commons.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.commons"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class CommonsConfig {

}
