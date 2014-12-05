package com.tmt.kontroll.ui.page.configuration.impl.components.containers;

import java.lang.annotation.Annotation;

import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.Scrollable;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configures the given {@link PageSegment} so that it becomes a scrollable container.
 * This means the segment will have to consist of two divs, instead of one. The segment
 * will be marked as scrollable by adding the <code>data-scrollable</code> attribute to
 * it. The front end modules responsible for rendering then have to take of handling the
 * two divs accordingly.
 *
 * @author SergDerbst
 *
 */
public class ScrollableConfigurator extends PageSegmentConfigurator {

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Scrollable.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		segment.getAttributes().put("data-scrollable", "");
	}
}
