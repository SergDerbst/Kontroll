package com.tmt.kontroll.ui.page.configuration;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.PageConfigurationFailedException;
import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.ContainerAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Caption;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;
import com.tmt.kontroll.ui.page.configuration.annotations.context.Page;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Prepares the given {@link PageSegment}'s annotations for configuration. It checks
 * the present {@link ConfigurationAnnotation}s for validity and returns a list of
 * configurations annotations in the correct order to be processed by the
 * {@link PageSegmentConfigurationHandler}.
 *
 * @author SergDerbst
 */
@Component
public class PageSegmentConfigurationHandlingPreparer {

	public List<Annotation> prepare(final PageSegment segment) {
		this.checkConfigurationValidity(segment);
		return this.prepareConfigurationAnnotations(segment);
	}

	private void checkConfigurationValidity(final PageSegment segment) {
		if (segment.getClass().isAnnotationPresent(Page.class) && segment.getClass().isAnnotationPresent(PageConfig.class)) {
			throw PageConfigurationFailedException.prepareValidity("Annotations @Page and @PageConfig cannot be present at the same page segment.", segment);
		}
		if (segment.getClass().isAnnotationPresent(Content.class) && segment.getClass().isAnnotationPresent(Caption.class)) {
			throw PageConfigurationFailedException.prepareValidity("Annotations @Content and @Caption cannot be present at the same page segment.", segment);
		}
	}

	private List<Annotation> prepareConfigurationAnnotations(final PageSegment segment) {
		final List<Annotation> configAnnos = new ArrayList<>();
		final Set<Annotation> elementAnnos = new TreeSet<>(new ChildElementOrdinalComparator());
		Content content = null;
		Caption caption = null;
		for (final Annotation annotation : segment.getClass().getAnnotations()) {
			if (this.isConfigurationAnnotation(annotation)) {
				if (Content.class.equals(annotation.annotationType())) {
					content = (Content) annotation;
					continue;
				}
				if (Caption.class.equals(annotation.annotationType())) {
					caption = (Caption) annotation;
					continue;
				}
				this.handleAnnotation(configAnnos, elementAnnos, annotation);
			}
		}
		configAnnos.addAll(elementAnnos);
		if (content != null) {
			configAnnos.add(content);
		}
		if (caption != null) {
			configAnnos.add(caption);
		}
		return configAnnos;
	}

	@SuppressWarnings("unchecked")
	private <A extends Annotation> void handleAnnotation(final List<Annotation> configAnnos, final Set<Annotation> elementAnnos, final Annotation annotation) {
		if (PageConfig.class.equals(annotation.annotationType()) || Page.class.equals(annotation.annotationType())) {
			this.addAnnotation(configAnnos, elementAnnos, annotation);
		} else {
			if (annotation.annotationType().isAnnotationPresent(ContainerAnnotation.class)) {
				for (final A repeatedAnno : (A[]) AnnotationUtils.getValue(annotation)) {
					this.addAnnotation(configAnnos, elementAnnos, repeatedAnno);
				}
			} else {
				this.addAnnotation(configAnnos, elementAnnos, annotation);
			}
		}
	}

	private void addAnnotation(final List<Annotation> configAnnos, final Set<Annotation> elementAnnos, final Annotation annotation) {
		if (annotation.annotationType().isAnnotationPresent(ChildElement.class)) {
			elementAnnos.add(annotation);
		} else {
			configAnnos.add(annotation);
		}
	}

	private boolean isConfigurationAnnotation(final Annotation annotation) {
		return annotation.annotationType().isAnnotationPresent(ConfigurationAnnotation.class);
	}

	private static class ChildElementOrdinalComparator implements Comparator<Annotation> {
		@Override
		public int compare(final Annotation a1, final Annotation a2) {
			try {
				return (Integer) a1.annotationType().getMethod("ordinal").invoke(a1) - (Integer) a2.annotationType().getMethod("ordinal").invoke(a2);
			} catch (final Exception e) {
				throw new PageConfigurationFailedException(e);
			}
		}
	}
}
