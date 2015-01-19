package com.tmt.kontroll.ui.page.preparation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.events.PageEvent;
import com.tmt.kontroll.ui.page.events.TriggerEventHolder;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentScopeContext;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

/**
 * Prepares the page after all {@link PageSegment}s have been configured by linking together
 * parent child relationships between page segments.
 *
 * @author SergDerbst
 *
 */
@Component
public class PagePreparator {

	@Autowired
	PageHolder														pageHolder;

	@Autowired
	PageSegmentHolder											pageSegmentHolder;

	@Autowired
	PageSegmentChildrenAndContentAccessor	childAndContentHandler;

	@Autowired
	TriggerEventHolder										triggerEventHolder;

	public void prepare() throws ScopeNotFoundException {
		for (final PageSegmentScopeContext scopeContext : this.pageSegmentHolder.fetchAllContexts()) {
			this.handleScope(scopeContext);
		}
	}

	private void handleScope(final PageSegmentScopeContext scopeContext) throws ScopeNotFoundException {
		final Set<PageSegment> segments = scopeContext.getSegments();
		for (final PageSegment segment : segments) {
			this.handleTriggerEvents(segment);
			this.addSegmentToParentScope(segment);
		}
	}

	private void handleTriggerEvents(final PageSegment segment) {
		final List<PageEvent> triggerEvents = this.triggerEventHolder.get(segment.getDomId());
		if (triggerEvents != null) {
			for (final PageEvent triggerEvent : triggerEvents) {
				segment.getGeneralEvents().put(triggerEvent.getType(), triggerEvent);
			}
		}
	}

	private void addSegmentToParentScope(final PageSegment segment) throws ScopeNotFoundException {
		if (this.isRootScope(segment)) {
			for (final String pattern : segment.getRequestPatterns()) {
				final PageSegment page = this.pageHolder.fetchPageByPattern(pattern);
				this.childAndContentHandler.addChild(page, segment);
			}
		} else {
			for (final PageSegment parentSegment : this.pageSegmentHolder.fetchPageSegments(segment.getParentScope())) {
				this.childAndContentHandler.addChild(parentSegment, segment);
			}
		}
	}

	private boolean isRootScope(final PageSegment segment) {
		return segment.getDomId().split("\\.").length == 2 && "page".equals(segment.getParentScope());
	}
}
