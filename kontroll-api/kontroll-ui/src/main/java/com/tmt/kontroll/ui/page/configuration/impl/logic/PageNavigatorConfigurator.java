package com.tmt.kontroll.ui.page.configuration.impl.logic;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.logic.PageNavigator;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;

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
	protected boolean isResponsible(final PageSegment segment) {
		return segment.getClass().isAnnotationPresent(PageNavigator.class);
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		final PageNavigator pageNavigator = segment.getClass().getAnnotation(PageNavigator.class);
		if (pageNavigator != null) {
			segment.getGeneralEvents().put(EventType.Click, this.createNavigationPageEvent(pageNavigator));
		}
	}

	private PageEvent createNavigationPageEvent(final PageNavigator pageNavigator) {
		final PageEvent event = new PageEvent(EventType.Click, "navigate");
		event.getArguments().put("url", pageNavigator.value());
		return event;
	}
}
