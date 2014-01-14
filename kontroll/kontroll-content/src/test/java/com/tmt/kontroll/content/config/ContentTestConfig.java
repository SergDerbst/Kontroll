package com.tmt.kontroll.content.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.persistence.commons.config.PersistenceTestConfig;

@Configuration
@ComponentScan(value = {"com.tmt.kontroll.content"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Import({PersistenceTestConfig.class})
public class ContentTestConfig extends ContentConfig {

}
