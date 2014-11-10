package com.tmt.kontroll.commons.utils.scanning;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

/**
 * Utility class that scans the given base packages of the classpath for
 * classes according to the given assignable type.
 *
 * @author SergDerbst
 *
 */
@Component
public class AssignableTypeScanner {

	private final boolean												useDefaultFilters	= false;
	ClassPathScanningCandidateComponentProvider	scanner						= new ClassPathScanningCandidateComponentProvider(this.useDefaultFilters);

	public Set<BeanDefinition> scan(final Class<?> assignableType, final List<String> basePackages) {
		final Set<BeanDefinition> set = new HashSet<BeanDefinition>();
		this.scanner.resetFilters(this.useDefaultFilters);
		this.scanner.addIncludeFilter(new AssignableTypeFilter(assignableType));
		for (final String basePackage : basePackages) {
			set.addAll(this.scanner.findCandidateComponents(basePackage));
		}
		return set;
	}
}
