package com.tmt.kontroll.ui.page.configuration.impl.event;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.annotations.event.HandlerArgument;
import com.tmt.kontroll.ui.page.events.PageEvent;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link PageConfig} according to the
 * configuration of {link Event} annotations present.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class EventConfigurator extends PageSegmentConfigurator {

	@Override
	protected boolean isResponsible(final PageSegment segment) {
		return segment.getClass().isAnnotationPresent(PageConfig.class);
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		final PageConfig config = segment.getClass().getAnnotation(PageConfig.class);
		for (final Event event : config.events()) {
			segment.getGeneralEvents().put(event.type(), this.createPageEvent(event));
		}
		for (final PageContext context : config.contexts()) {
			for (final Event event : context.events()) {
				segment.getAdditionalEvents().put(event.type(), this.createPageEvent(event));
			}
		}
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
