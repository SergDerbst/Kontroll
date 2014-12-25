package com.tmt.kontroll.ui.page.configuration.impl.event;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.EventConfigurationHandler;
import com.tmt.kontroll.ui.page.segments.PageSegment;

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

	@Autowired
	EventConfigurationHandler	eventHandler;

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Event.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		final PageConfig config = segment.getClass().getAnnotation(PageConfig.class);
		for (final Event event : config.events()) {
			this.eventHandler.handle(event, segment);
		}
		for (final PageContext context : config.contexts()) {
			for (final Event event : context.events()) {
				this.eventHandler.handle(event, segment);
			}
		}
	}
}
