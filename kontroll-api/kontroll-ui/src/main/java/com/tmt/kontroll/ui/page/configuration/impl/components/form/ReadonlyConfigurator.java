package com.tmt.kontroll.ui.page.configuration.impl.components.form;

import java.lang.annotation.Annotation;

import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.Readonly;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Configures {@link PageSegment}s annotated with {@link Readonly}. Essentially,
 * it will just add an empty attribute <code>readonly</code> to it.
 *
 * @author SergDerbst
 *
 */
public class ReadonlyConfigurator extends PageSegmentConfigurator {

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Readonly.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		segment.getAttributes().put("readonly", "");
	}
}
