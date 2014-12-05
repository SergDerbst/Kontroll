package com.tmt.kontroll.ui.page.configuration;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Responsibility chain handler for configuring {@link PageSegment}s during preparation phase.
 *
 * @author SergDerbst
 *
 */
@Component
public class PageSegmentConfigurationHandler {

	@Autowired
	PageSegmentConfigurationHandlingPreparer																configurationPreparer;

	private final Map<Class<? extends Annotation>, PageSegmentConfigurator>	configurators	= new HashMap<>();

	public void configure(final PageSegment segment) {
		for (final Annotation annotation : this.configurationPreparer.prepare(segment)) {
			final PageSegmentConfigurator configurator = this.configurators.get(annotation.annotationType());
			configurator.configure(segment);
		}
		segment.setConfigured(true);
	}

	public void addConfigurator(final PageSegmentConfigurator configurator) {
		this.configurators.put(configurator.getAnnotationType(), configurator);
	}
}