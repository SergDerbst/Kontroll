package com.tmt.kontroll.ui.page.configuration.impl.general;

import java.lang.annotation.Annotation;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.general.Hidden;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * Configures {@link PageSegment}s annotated with {@link Hidden}.
 * </p>
 * <p>
 * During preparation phase the page segment's attributes are extended with the <code>hidden</code>
 * attribute causing it to be rendered invisible.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class HiddenConfigurator extends PageSegmentConfigurator {

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Hidden.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		segment.getAttributes().put("hidden", "");
	}
}
