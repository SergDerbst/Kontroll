package com.tmt.kontroll.ui.page.configuration.impl.components.containers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections.List;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Event;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.EventConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ItemsSourceConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for the {@link List} annotation. It adds a list container child element
 * to the given {@link PageSegment}. List containers are divs
 *
 * @author SergDerbst
 *
 */
@Component
public class ListConfigurator extends ChildElementConfigurator {

	@Autowired
	ItemsSourceConfigurationHandler	itemSourceHandler;

	@Autowired
	EventConfigurationHandler				eventHandler;

	public ListConfigurator() {
		super(List.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		for (final List config : segment.getClass().getAnnotationsByType(List.class)) {
			if (super.isNotAddedYet(segment, config, "name")) {
				final PageSegment list = new PageSegment() {};
				list.setParentScope(segment.getDomId());
				list.setScope(config.name());
				list.getAttributes().put("data-list", "");
				this.itemSourceHandler.handle(config.itemsSource(), list);
				for (final Event event : config.events()) {
					this.eventHandler.handle(event, list);
				}
				super.addChild(config.position(), segment, list);
			}
		}
	}
}
