package com.tmt.kontroll.ui.page.configuration.helpers.providers;

import java.util.List;

import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * Provides a list of {@link PageSegment}s.
 *
 * @author SergDerbst
 *
 */
public interface ItemProvider {

	public List<PageSegment> provide(final PageSegment parentSegment);
}
