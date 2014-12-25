package com.tmt.kontroll.ui.page.configuration.helpers.handlers;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.json.DataTransferElement;
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
public class EventConfigurationHandler {

	public void handle(final Event event, final PageSegment segment) {
		final PageEvent pageEvent = new PageEvent(event.type(), event.handlers());
		this.handleTargetScopes(event, pageEvent);
		this.handleEventArguments(event, pageEvent);
		segment.getGeneralEvents().put(event.type(), pageEvent);
	}

	private void handleEventArguments(final Event event, final PageEvent pageEvent) {
		for (final HandlerArgument argument : event.arguments()) {
			pageEvent.getArguments().put(argument.key(), argument.value());
		}
		if (!DataTransferElement.class.equals(event.dto())) {
			pageEvent.getArguments().put("dtoClass", event.dto().getName());
		}
	}

	private void handleTargetScopes(final Event event, final PageEvent pageEvent) {
		pageEvent.getArguments().put("targetScopes", event.targetScopes());
	}
}