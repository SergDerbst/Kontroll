package com.tmt.kontroll.persistence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;



/**
 * Configuration class to set up the Spring persistence context.
 * 
 * @author Serg Derbst
 *
 */
@Configuration
@ComponentScan(value = {"com.tmt.kontroll.persistence"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Import({GeneralContextConfig.class, Jsr303BeanValidationConfig.class, JpaConfig.class})
public class PersistenceConfig {

}
