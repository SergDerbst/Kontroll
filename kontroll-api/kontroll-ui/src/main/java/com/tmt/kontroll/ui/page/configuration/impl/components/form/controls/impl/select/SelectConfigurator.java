package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.Select;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;
import com.tmt.kontroll.ui.page.configuration.helpers.creators.LabelCreator;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ItemsSourceConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ValueSourceConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for {@link PageSegment}s annotated with {@link Select}. It adds a select control
 * as child element to the page segment according to the annotation's configuration.
 *
 * @author SergDerbst
 *
 */
@Component
public class SelectConfigurator extends ChildElementConfigurator {

	@Autowired
	LabelCreator				labelCreator;

	@Autowired
	ItemsSourceConfigurationHandler	itemSourceHandler;

	@Autowired
	ValueSourceConfigurationHandler	valueSourceHandler;

	public SelectConfigurator() {
		super(Select.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		for (final Select config : segment.getClass().getAnnotationsByType(Select.class)) {
			if (super.isNotAddedYet(segment, config, "name")) {
				final PageSegment select = new PageSegment(HtmlTag.Select) {};
				select.setParentScope(segment.getDomId());
				select.setScope(config.name());
				this.handleAttributes(config, select);
				this.handleValueSource(config, select);
				this.handleLabel(config, select, segment);
				this.itemSourceHandler.handle(config.optionsSource(), select);
				super.addChild(config.position(), segment, select);
			}
		}
	}

	private void handleLabel(final Select config, final PageSegment input, final PageSegment segment) {
		super.addChild(config.position(), segment, this.labelCreator.create(config.label(), input));
	}

	private void handleValueSource(final Select config, final PageSegment select) {
		if (this.isValueSourceToBeHandled(config)) {
			this.valueSourceHandler.handle(config.valueSource(), select);
		}
	}

	private boolean isValueSourceToBeHandled(final Select config) {
		return ValueSourceType.None != config.valueSource().type() || ValueSourceType.Custom != config.valueSource().type() || ValueSourceType.Caption != config.valueSource().type();
	}

	private void handleAttributes(final Select config, final PageSegment select) {
		select.getAttributes().put("name", config.name());
		if (config.hidden()) {
			select.getAttributes().put("hidden", "");
		}
		if (config.readonly()) {
			select.getAttributes().put("readonly", "");
		}
	}
}
