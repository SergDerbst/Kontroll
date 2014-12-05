package com.tmt.kontroll.ui.page.configuration.impl.logic;

import java.lang.annotation.Annotation;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.logic.PageNavigator;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link PageNavigator}.
 * </p>
 * <p>
 *
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class PageNavigatorConfigurator extends PageSegmentConfigurator {

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return PageNavigator.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		final PageNavigator pageNavigator = segment.getClass().getAnnotation(PageNavigator.class);
		if (pageNavigator != null) {
			segment.getGeneralEvents().put(EventType.Click, this.createNavigationPageEvent(pageNavigator));
		}
	}

	private PageEvent createNavigationPageEvent(final PageNavigator pageNavigator) {
		final PageEvent event = new PageEvent(EventType.Click, new String[] {"navigate"});
		event.getArguments().put("url", pageNavigator.value());
		return event;
	}
}
