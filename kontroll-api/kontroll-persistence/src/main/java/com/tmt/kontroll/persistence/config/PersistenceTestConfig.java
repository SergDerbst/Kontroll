package com.tmt.kontroll.persistence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.persistence.config.GeneralContextConfig;
import com.tmt.kontroll.persistence.config.Jsr303BeanValidationConfig;

@Configuration
@Import(value = {KontrollTestJpaConfig.class, Jsr303BeanValidationConfig.class, GeneralContextConfig.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(value = {"com.tmt.kontroll.persistence"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class PersistenceTestConfig {

}
