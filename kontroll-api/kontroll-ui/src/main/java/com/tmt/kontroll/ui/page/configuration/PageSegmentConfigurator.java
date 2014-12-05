package com.tmt.kontroll.ui.page.configuration;

import java.lang.annotation.Annotation;

import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Abstract base class for all {@link PageSegment} configurators. It contains two methods,
 * one returning the annotation type the configurator is for and the other for performing
 * the configuration.
 *
 * @author SergDerbst
 *
 */
public abstract class PageSegmentConfigurator {

	protected abstract Class<? extends Annotation> getAnnotationType();

	public abstract void configure(final PageSegment segment);
}
