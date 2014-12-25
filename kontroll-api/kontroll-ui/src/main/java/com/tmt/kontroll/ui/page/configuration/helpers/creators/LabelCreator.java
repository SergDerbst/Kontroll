package com.tmt.kontroll.ui.page.configuration.helpers.creators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;
import com.tmt.kontroll.ui.page.configuration.enums.components.ValueSourceType;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CaptionConfigurationHandler;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.ValueSourceConfigurationHandler;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

/**
 * Creates a {@link PageSegment} as label according to the given {@link Label} configuration.
 * The created label is added to the {@link PageSegmentHolder} and returned.
 *
 * @author SergDerbst
 *
 */
@Component
public class LabelCreator {

	private static final String	FormControlLabelSuffix	= "Label";

	@Autowired
	CaptionConfigurationHandler							captionHandler;

	@Autowired
	ValueSourceConfigurationHandler					valueSourceHandler;

	@Autowired
	PageSegmentHolder						segmentHolder;

	public PageSegment create(final PageSegment segment) {
		return this.create(segment.getClass().getAnnotation(Label.class), segment);
	}

	public PageSegment create(final Label config, final PageSegment segment) {
		if (ValueSourceType.None == config.value().type()) {
			return null;
		} else {
			final PageSegment label = new PageSegment(HtmlTag.Label) {};
			label.setParentScope(segment.getParentScope());
			label.setScope(segment.getScope() + FormControlLabelSuffix);
			label.getAttributes().put("for", segment.getAttributes().get("name"));
			if (ValueSourceType.Caption == config.value().type()) {
				this.captionHandler.handle(config, label, config.value().source(), label.getScope());
			} else {
				this.valueSourceHandler.handle(config.value(), label);
			}
			this.segmentHolder.addPageSegment(label.getDomId(), label);
			return label;
		}
	}
}
