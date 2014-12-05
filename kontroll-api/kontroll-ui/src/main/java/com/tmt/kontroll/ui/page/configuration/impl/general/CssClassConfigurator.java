package com.tmt.kontroll.ui.page.configuration.impl.general;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.general.CssClass;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssHandler;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for the {@link CssClass} annotation. It calls the {@link CssHandler} to
 * add the value of the annotation to the class attribute of the given {@link PageSegment}.
 *
 * @author SergDerbst
 *
 */
@Component
public class CssClassConfigurator extends PageSegmentConfigurator {

	@Autowired
	CssHandler	cssHandler;

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return CssClass.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		this.cssHandler.handle(segment, segment.getClass().isAnnotationPresent(CssClass.class) ? segment.getClass().getAnnotation(CssClass.class).value() : "");
	}
}
