package com.tmt.kontroll.ui.page.configuration.impl.components.containers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.collections.Dropdown;
import com.tmt.kontroll.ui.page.configuration.annotations.event.Trigger;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.helpers.providers.ItemProvider;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.events.TriggerEventHolder;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for the {@link Dropdown} annotation. It adds a dropdown container child element to
 * the given {@link PageSegment}.
 *
 * @author SergDerbst
 *
 */
@Component
public class DropdownConfigurator extends ChildElementConfigurator {

	@Autowired
	ApplicationContext	applicationContext;

	@Autowired
	TriggerEventHolder	triggerEventHolder;

	protected DropdownConfigurator() {
		super(Dropdown.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		for (final Dropdown config : segment.getClass().getAnnotationsByType(Dropdown.class)) {
			if (super.isNotAddedYet(segment, config, "name")) {
				final PageSegment dropdown = new PageSegment() {};
				dropdown.setParentScope(segment.getDomId());
				dropdown.setScope(config.name());
				this.handleInitialVisibility(dropdown, config);
				this.handleItems(dropdown, config);
				this.handleTrigger(dropdown, config);
				super.addChild(ChildPosition.Top, segment, dropdown);
			}
		}
	}

	private void handleTrigger(final PageSegment dropdown, final Dropdown config) {
		for (final Trigger trigger : config.triggers()) {
			final PageEvent event = new PageEvent(trigger.type(), new String[] {"toggleVisibility"});
			event.getArguments().put("targetScope", dropdown.getDomId());
			this.triggerEventHolder.add(trigger.value(), event);
		}
	}

	private void handleInitialVisibility(final PageSegment dropdown, final Dropdown config) {
		if (!config.visible()) {
			dropdown.getAttributes().put("hidden", "");
		}
	}

	private void handleItems(final PageSegment dropdown, final Dropdown config) {
		final ItemProvider itemProvider = this.applicationContext.getBean(config.itemProvider());
		for (final PageSegment item : itemProvider.provide(dropdown)) {
			super.addChild(ChildPosition.Top, dropdown, item);
		}
	}
}
