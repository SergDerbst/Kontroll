package com.tmt.kontroll.ui.page.configuration.helpers.handlers;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.annotations.event.HandlerArgument;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Handles the event configuration for the given {@link PageSegment} according
 * to the values of the given {@link Event} annotation.
 *
 * @author SergDerbst
 *
 */
@Component
public class EventHandler {

	public void handle(final Event event, final PageSegment segment) {
		final PageEvent pageEvent = new PageEvent(event.type(), event.handlers());
		pageEvent.getArguments().put("targetScopes", event.targetScopes());
		for (final HandlerArgument argument : event.arguments()) {
			pageEvent.getArguments().put(argument.key(), argument.value());
		}
		segment.getGeneralEvents().put(event.type(), pageEvent);
	}
}