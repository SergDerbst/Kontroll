package com.tmt.kontroll.ui.page.configuration.impl.components.containers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.PageConfigurationFailedException;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.components.containers.Scrollable;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssConfigurationHandler;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

/**
 * Configures {@link PageSegment}s annotated with {@link Scrollable}. Scrollable
 * page segments consist of two div containers, whereas the first is the overall
 * container and the second is the scroller container. The first has
 * <code>scrollContainer</code> as extra css class, and the second has
 * <code>scroller</code>.
 *
 * @author SergDerbst
 *
 */
@Component
public class ScrollableConfigurator extends PageSegmentConfigurator {

	@Autowired
	CssConfigurationHandler				cssHandler;

	@Autowired
	PageSegmentHolder	segmentHolder;

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return Scrollable.class;
	}

	@Override
	public void configure(final PageSegment segment) {
		final PageSegment scroller = new PageSegment() {};
		scroller.setParentScope(segment.getDomId());
		scroller.setScope("scroller");
		this.cssHandler.handle(segment, "scrollContainer");
		this.cssHandler.handle(scroller);
		this.addScrollerToSegment(segment, scroller);
	}

	@SuppressWarnings("unchecked")
	private void addScrollerToSegment(final PageSegment segment, final PageSegment scroller) {
		try {
			final Method getMainChildren = PageSegment.class.getDeclaredMethod("getMainChildren");
			getMainChildren.setAccessible(true);
			((Map<PageSegmentOrdinalKey, PageSegment>) getMainChildren.invoke(segment)).put(new PageSegmentOrdinalKey(0, scroller.getDomId()), scroller);
		} catch (final Exception e) {
			throw new PageConfigurationFailedException(e);
		}
	}
}
