package com.tmt.kontroll.business.preparation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.tmt.kontroll.business.BusinessPersistenceEntityMapper;
import com.tmt.kontroll.business.annotations.BusinessEntity;
import com.tmt.kontroll.business.config.BusinessProperties;
import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.scanning.AnnotationCandidateScanner;

/**
 * The business entity scanner scans all given base packages for classes annotated with
 * {@link BusinessEntity} and adds their classes to the {@link BusinessPersistenceEntityMapper}
 * mapped to the persistence entity class of the annotation's value.
 *
 * @author SergDerbst
 *
 */
@Component
public class BusinessEntityScanner {

	@Autowired
	BusinessProperties							businessProperties;

	@Autowired
	AnnotationCandidateScanner			candidateScanner;

	@Autowired
	BusinessPersistenceEntityMapper	persistenceBusinessEntityMapper;

	public void scan() {
		try {
			for (final BeanDefinition beanDefinition : this.candidateScanner.scan(BusinessEntity.class, this.businessProperties.businessEntityBasePackages())) {
				final Class<?> businessEntityClass = ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
				this.persistenceBusinessEntityMapper.addMapping(businessEntityClass.getAnnotation(BusinessEntity.class).value(), businessEntityClass);
			}
		} catch (final Exception e) {
			throw new ScanFailedException(e);
		}
	}
}
