package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.textarea.Textarea;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;
import com.tmt.kontroll.ui.page.configuration.helpers.creators.LabelCreator;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ValueSourceConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for {@link PageSegment}s annotated with {@link Textarea}. It adds a select control
 * as child element to the page segment according to the annotation's configuration.
 *
 * @author SergDerbst
 *
 */
@Component
public class TextareaConfigurator extends ChildElementConfigurator {

	@Autowired
	LabelCreator				labelCreator;

	@Autowired
	ValueSourceConfigurationHandler	valueSourceHandler;

	protected TextareaConfigurator() {
		super(Textarea.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		for (final Textarea config : segment.getClass().getAnnotationsByType(Textarea.class)) {
			if (super.isNotAddedYet(segment, config, "name")) {
				final PageSegment textarea = new PageSegment(HtmlTag.Textarea) {};
				textarea.setParentScope(segment.getDomId());
				textarea.setScope(config.name());
				this.handleAttributes(config, textarea);
				this.handleValueSource(config, textarea);
				this.handleLabel(config, textarea, segment);
				super.addChild(config.position(), segment, textarea);
			}
		}
	}

	private void handleAttributes(final Textarea config, final PageSegment textarea) {
		textarea.getAttributes().put("name", config.name());
		textarea.getAttributes().put("row", String.valueOf(config.rows()));
		textarea.getAttributes().put("cols", String.valueOf(config.cols()));
		textarea.getAttributes().put("wrap", String.valueOf(config.wrap().getValue()));
		if (config.hidden()) {
			textarea.getAttributes().put("hidden", "");
		}
		if (config.readonly()) {
			textarea.getAttributes().put("readonly", "");
		}
	}

	private void handleValueSource(final Textarea config, final PageSegment select) {
		if (this.isValueSourceToBeHandled(config)) {
			this.valueSourceHandler.handle(config.valueSource(), select);
		}
	}

	private boolean isValueSourceToBeHandled(final Textarea config) {
		return ValueSourceType.None != config.valueSource().type() || ValueSourceType.Custom != config.valueSource().type() || ValueSourceType.Caption != config.valueSource().type();
	}

	private void handleLabel(final Textarea config, final PageSegment textarea, final PageSegment segment) {
		super.addChild(config.position(), segment, this.labelCreator.create(config.label(), textarea));
	}
}
