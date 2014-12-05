package com.tmt.kontroll.ui.page.configuration.helpers.handlers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.Select;
import com.tmt.kontroll.ui.page.configuration.annotations.components.form.controls.select.SelectControl;
import com.tmt.kontroll.ui.page.configuration.annotations.config.ItemsSource;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Handles the options configuration according to the {@link ItemsSource} within the
 * {@link Select} or {@link SelectControl} annotation on the given {@link PageSegment} by adding two
 *
 * @author SergDerbst
 *
 */
@Component
public class ItemsSourceHandler {

	public void handle(final ItemsSource itemsSource, final PageSegment segment) {
		if (!itemsSource.source().isEmpty()) {
			segment.getConfigOptions().put("itemsSourceType", StringUtils.uncapitalize(itemsSource.type().name()));
			segment.getConfigOptions().put("itemsSource", itemsSource.source());
		}
	}
}
