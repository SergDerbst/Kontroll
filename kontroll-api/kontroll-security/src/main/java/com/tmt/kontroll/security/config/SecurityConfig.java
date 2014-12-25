package com.tmt.kontroll.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.tmt.kontroll.commons.config.KontrollModuleProperties;
import com.tmt.kontroll.persistence.config.PersistenceConfig;

@Configuration
@Import({PersistenceConfig.class})
@ComponentScan(value = {"com.tmt.kontroll.security"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@EnableJpaRepositories(basePackages = {"com.tmt.kontroll.security.persistence.repositories"}, entityManagerFactoryRef = "entityManagerFactoryBean", transactionManagerRef = "transactionManager")
@EnableAspectJAutoProxy
public class SecurityConfig extends KontrollModuleProperties {

}
