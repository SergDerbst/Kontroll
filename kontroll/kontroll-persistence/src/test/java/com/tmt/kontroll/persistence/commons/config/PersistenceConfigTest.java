package com.tmt.kontroll.persistence.commons.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.tmt.kontroll.persistence.config.GeneralContextConfig;
import com.tmt.kontroll.persistence.config.Jsr303BeanValidationConfig;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(value = {"de.sd.tryout.persistence"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Import(value = {JpaConfigTest.class, Jsr303BeanValidationConfig.class, GeneralContextConfig.class})
public class PersistenceConfigTest {

}
