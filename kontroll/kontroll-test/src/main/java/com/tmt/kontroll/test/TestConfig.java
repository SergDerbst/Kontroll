package com.tmt.kontroll.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class TestConfig {

}
