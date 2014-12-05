package com.tmt.kontroll.ui.page.configuration.helpers.handlers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.annotations.config.ValueSource;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Handles configuration of {@link ValueSource} for the given {@link PageSegment}
 * by adding according data attributes for {@link ValueSource#type} and {@link ValueSource#source()}.
 *
 * @author SergDerbst
 *
 */
@Component
public class ValueSourceHandler {

	public void handle(final ValueSource valueSource, final PageSegment segment) {
		if (!valueSource.source().isEmpty()) {
			segment.getAttributes().put("data-valueSourceType", StringUtils.uncapitalize(valueSource.type().name()));
			segment.getAttributes().put("data-valueSource", StringUtils.uncapitalize(valueSource.source()));
		}
	}
}
