package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.HierarchicalSelect;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.helpers.creators.LabelCreator;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for {@link PageSegment}s annotated with {@link HierarchicalSelect}. It adds two select
 * controls as child elements to the page segment according to the annotation's configuration, whereas
 * the first select control is the master of the second slave select control, meaning that the selected
 * value of the master determines the select options of the slave according to the contents of the
 * {@link PageSegment#getOptionsMap}.
 *
 * @author SergDerbst
 *
 */
@Component
public class HierarchicalSelectConfigurator extends ChildElementConfigurator {

	@Autowired
	CssConfigurationHandler	cssHandler;

	@Autowired
	LabelCreator						labelCreator;

	public HierarchicalSelectConfigurator() {
		super(HierarchicalSelect.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		final HierarchicalSelect config = segment.getClass().getAnnotation(HierarchicalSelect.class);
		final PageSegment container = this.createContainer(config, segment);
		final PageSegment master = this.createMaster(config, container, segment);
		final PageSegment slave = this.createSlave(config, container);
		final PageSegment masterLabel = this.labelCreator.create(config.label(), master);
		final PageSegment slaveLabel = this.labelCreator.create(config.label(), slave);
		super.addChild(ChildPosition.Top, container, masterLabel);
		super.addChild(ChildPosition.Top, container, master);
		super.addChild(ChildPosition.Top, container, slaveLabel);
		super.addChild(ChildPosition.Top, container, slave);
	}

	private PageSegment createContainer(final HierarchicalSelect config, final PageSegment segment) {
		final PageSegment container = new PageSegment() {};
		container.setParentScope(segment.getDomId());
		container.setScope(config.name());
		this.cssHandler.handle(container);
		super.addChild(config.position(), segment, container);
		return container;
	}

	private PageSegment createMaster(final HierarchicalSelect config, final PageSegment container, final PageSegment segment) {
		final PageSegment master = new PageSegment(HtmlTag.Select) {};
		master.setParentScope(container.getDomId());
		master.setScope(config.master());
		master.getAttributes().put("name", config.master());
		master.getConfigOptions().put("hierarchical", "master");
		this.cssHandler.handle(master);
		final PageEvent selectEvent = new PageEvent(EventType.Change, new String[] {"masterSelect"});
		selectEvent.getArguments().put("targetScope", container.getDomId() + "." + config.slave());
		selectEvent.getArguments().put("optionsMapScope", segment.getDomId());
		master.getGeneralEvents().put(EventType.Change, selectEvent);
		return master;
	}

	private PageSegment createSlave(final HierarchicalSelect config, final PageSegment container) {
		final PageSegment slave = new PageSegment(HtmlTag.Select) {};
		slave.setParentScope(container.getDomId());
		slave.setScope(config.slave());
		slave.getAttributes().put("name", config.slave());
		slave.getConfigOptions().put("hierarchical", "slave");
		this.cssHandler.handle(slave);
		return slave;
	}
}
