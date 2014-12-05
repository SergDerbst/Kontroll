package com.tmt.kontroll.ui.page.configuration.impl.context;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.context.Page;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssHandler;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for the {@link Page} annotation. It adds the page to the {@link PageHolder}.
 *
 * @author SergDerbst
 *
 */
@Component
public class PageConfigurator extends PageSegmentConfigurator {

	@Autowired
	CssHandler	cssHandler;

	@Autowired
	PageHolder	pageHolder;

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Page.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		final Page config = segment.getClass().getAnnotation(Page.class);
		segment.setScope("page");
		this.cssHandler.handle(segment);
		this.pageHolder.addPage(config.value(), segment);
	}
}
