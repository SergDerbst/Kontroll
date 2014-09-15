package com.tmt.kontroll.commons.utils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.stereotype.Component;

/**
 * Utility class that scans the given base packages of the classpath and searches
 * for classes according to their assignable type and annotation. 
 * 
 * @author Sergio Weigel
 *
 */
@Component
public class AnnotationAndAssignableTypeCandidateScanner {

	private final boolean useDefaultFilters = false;
	ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(this.useDefaultFilters);
	
	public Set<BeanDefinition> scan(final Class<? extends Annotation> annotationType,
	                                final Class<?> assignableType,
	                                final List<String> basePackages) {
		final Set<BeanDefinition> set = new HashSet<BeanDefinition>();
		scanner.resetFilters(this.useDefaultFilters);
		scanner.addIncludeFilter(new AnnotationAndAssignableTypeFilter(annotationType, assignableType));
		for (final String basePackage : basePackages) {
			set.addAll(scanner.findCandidateComponents(basePackage));
		}
		return set;
	}
}
