package com.tmt.kontroll.ui.page.preparation;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.events.Event;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.HandlerArgument;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.layout.navigator.PageNavigator;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;

/**
 * <p>
 * The page event configurator is responsible for configuring {@link PageSegment}s with client side
 * events, their handlers and arguments that need to be passed to these handlers on the client side.
 * </p>
 * <p>
 * The configuration follows the {@link EventType} and {@link PageNavigator} annotations added to the
 * respective page segment on class level. For each configured event type, a {@link PageEvent} is
 * created containing the type of event, the name of the handler (managed by the client) and a list
 * of arguments passed to the handler when the event occurs (also managed by the client).
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class PageEventConfigurator {

	public void configure(final PageSegment segment, final PageConfig pageConfig, final PageContext pageContext) {
		if (segment.getGeneralEvents().isEmpty()) {
			this.handleNavigationConfiguration(segment);
			for (final Event event : pageConfig.events()) {
				segment.getGeneralEvents().put(event.type(), this.createPageEvent(event));
			}
		}
		for (final Event event : pageContext.events()) {
			segment.getAdditionalEvents().put(event.type(), this.createPageEvent(event));
		}
	}

	private void handleNavigationConfiguration(final PageSegment segment) {
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

	private PageEvent createPageEvent(final Event event) {
		final PageEvent pageEvent = new PageEvent(event.type(), event.handler());
		pageEvent.getArguments().put("targetScopes", event.targetScopes());
		for (final HandlerArgument argument : event.arguments()) {
			pageEvent.getArguments().put(argument.key(), argument.value());
		}
		return pageEvent;
	}
}
