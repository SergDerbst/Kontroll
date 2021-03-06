package com.tmt.kontroll.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.test"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class TestConfig {

}
