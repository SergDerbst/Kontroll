package com.tmt.kontroll.ui.page.configuration.impl.components.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.PageSegmentHolder;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.label.Label;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link Label}.
 * </p>
 * <p>
 * Assuming that the annotated segment is a form control, it will create another page segment
 * within the same scope with tag {@link HtmlTag#Label}. The label's <code>for</code> attribute
 * will be set to the value of the segment's <code>name</code> attribute, thus creating a label
 * for the form control.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class LabelConfigurator extends PageSegmentConfigurator {

	@Autowired
	PageSegmentHolder	pageSegmentHolder;

	@Override
	protected boolean isResponsible(final PageSegment segment) {
		return segment.getClass().isAnnotationPresent(Label.class);
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		final PageSegment label = new PageSegment(HtmlTag.Label) {};
		label.setParentScope(segment.getParentScope());
		label.setScope(segment.getScope() + "Label");
		label.getAttributes().put("for", segment.getAttributes().get("name"));
		this.pageSegmentHolder.addPageSegment(label.getDomId(), segment);
	}
}
