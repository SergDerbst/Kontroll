package com.tmt.kontroll.ui.page.configuration.impl.components.form.controls.impl.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.input.Input;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;
import com.tmt.kontroll.ui.page.configuration.helpers.creators.LabelCreator;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ValueSourceConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.impl.components.layout.ChildElementConfigurator;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configurator for {@link PageSegment}s annotated with {@link Input}. It adds an input child
 * element to the annotated page segment according to the annotation's values.
 *
 * @author SergDerbst
 *
 */
@Component
public class InputConfigurator extends ChildElementConfigurator {

	@Autowired
	LabelCreator				labelCreator;

	@Autowired
	ValueSourceConfigurationHandler	valueSourceHandler;

	public InputConfigurator() {
		super(Input.class);
	}

	@Override
	public void configure(final PageSegment segment) {
		for (final Input config : segment.getClass().getAnnotationsByType(Input.class)) {
			if (super.isNotAddedYet(segment, config, "name")) {
				final PageSegment input = new PageSegment(HtmlTag.Input) {};
				input.setParentScope(segment.getDomId());
				input.setScope(config.name());
				this.handleAttributes(config, input);
				this.handleValueSource(config, input);
				this.handleLabel(config, input, segment);
				super.addChild(config.position(), segment, input);
			}
		}
	}

	private void handleLabel(final Input config, final PageSegment input, final PageSegment segment) {
		final PageSegment label = this.labelCreator.create(config.label(), input);
		if (label != null) {
			super.addChild(config.position(), segment, label);
		}
	}

	private void handleValueSource(final Input config, final PageSegment input) {
		if (ValueSourceType.Caption != config.valueSource().type()) {
			this.valueSourceHandler.handle(config.valueSource(), input);
		}
	}

	private void handleAttributes(final Input config, final PageSegment input) {
		input.getAttributes().put("name", config.name());
		input.getAttributes().put("type", config.type());
		if (config.readonly()) {
			input.getAttributes().put("readonly", "");
		}
		if (config.hidden()) {
			input.getAttributes().put("hidden", "");
		}
	}
}
