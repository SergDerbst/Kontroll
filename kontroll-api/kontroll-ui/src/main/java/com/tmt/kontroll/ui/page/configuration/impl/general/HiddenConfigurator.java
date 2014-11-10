package com.tmt.kontroll.ui.page.configuration.impl.general;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.general.Hidden;

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
	protected boolean isResponsible(final PageSegment segment) {
		return segment.getClass().isAnnotationPresent(Hidden.class);
	}

	@Override
	protected void doConfiguration(final PageSegment segment) {
		segment.getAttributes().put("hidden", "");
	}
}
