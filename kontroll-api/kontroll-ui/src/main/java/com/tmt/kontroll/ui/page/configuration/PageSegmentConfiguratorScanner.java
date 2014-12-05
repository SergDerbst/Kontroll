package com.tmt.kontroll.ui.page.configuration;

import java.lang.reflect.Modifier;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.scanning.AssignableTypeScanner;
import com.tmt.kontroll.ui.config.UiProperties;

@Component
public class PageSegmentConfiguratorScanner {

	@Autowired
	UiProperties										uiProperties;

	@Autowired
	AssignableTypeScanner						candidateScanner;

	@Autowired
	ApplicationContext							applicationContext;

	@Autowired
	PageSegmentConfigurationHandler	configurationHandler;

	public void scan() {
		try {
			for (final BeanDefinition beanDefinition : this.candidateScanner.scan(PageSegmentConfigurator.class, this.uiProperties.basePackages())) {
				@SuppressWarnings("unchecked")
				final Class<? extends PageSegmentConfigurator> configuratorClass = (Class<? extends PageSegmentConfigurator>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
				if (!Modifier.isAbstract(configuratorClass.getModifiers())) {
					PageSegmentConfigurator configurator = null;
					try {
						configurator = this.applicationContext.getBean(configuratorClass);
					} catch (final NoSuchBeanDefinitionException e) {
						configurator = configuratorClass.newInstance();
					}
					this.configurationHandler.addConfigurator(configurator);
				}
			}
		} catch (final Exception e) {
			throw new ScanFailedException(e);
		}
	}
}
