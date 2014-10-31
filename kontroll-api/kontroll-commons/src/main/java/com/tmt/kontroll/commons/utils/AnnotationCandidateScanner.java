package com.tmt.kontroll.commons.utils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

/**
 * Utility class that scans the given base packages of the classpath for
 * classes according to their annotation.
 *
 * @author SergDerbst
 *
 */
@Component
public class AnnotationCandidateScanner {

	private final boolean												useDefaultFilters	= false;
	ClassPathScanningCandidateComponentProvider	scanner						= new ClassPathScanningCandidateComponentProvider(this.useDefaultFilters);

	public Set<BeanDefinition> scan(final Class<? extends Annotation> annotationType, final List<String> basePackages) {
		final Set<BeanDefinition> set = new HashSet<BeanDefinition>();
		this.scanner.resetFilters(this.useDefaultFilters);
		this.scanner.addIncludeFilter(new AnnotationTypeFilter(annotationType));
		for (final String basePackage : basePackages) {
			set.addAll(this.scanner.findCandidateComponents(basePackage));
		}
		return set;
	}
}
