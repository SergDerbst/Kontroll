package com.tmt.kontroll.ui.page.preparation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.Page;
import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.PageSegmentHolder;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentScopeContext;

@Component
public class PagePreparator {

	@Autowired
	PageHolder				pageHolder;

	@Autowired
	PageSegmentHolder	pageSegmentHolder;

	public void prepare() throws ScopeNotFoundException {
		for (final PageSegmentScopeContext scopeContext : this.pageSegmentHolder.fetchAllContexts()) {
			this.handleScope(scopeContext);
		}
	}

	private void handleScope(final PageSegmentScopeContext scopeContext) throws ScopeNotFoundException {
		final Set<PageSegment> segments = scopeContext.getSegments();
		for (final PageSegment segment : segments) {
			this.addSegmentToParentScope(segment);
		}
	}

	private void addSegmentToParentScope(final PageSegment segment) throws ScopeNotFoundException {
		if (this.isRootScope(segment)) {
			for (final String pattern : segment.getRequestPatterns()) {
				final Page page = this.pageHolder.fetchPageByPattern(pattern);
				page.getChildren().put(new PageSegmentOrdinalKey(segment.getOrdinal(), segment.getDomId()), segment);
			}
		} else {
			for (final PageSegment parentSegment : this.pageSegmentHolder.fetchPageSegments(segment.getParentScope())) {
				parentSegment.getChildren().put(new PageSegmentOrdinalKey(segment.getOrdinal(), segment.getDomId()), segment);
			}
		}
	}

	private boolean isRootScope(final PageSegment segment) {
		return segment.getDomId().split("\\.").length == 2 && "page".equals(segment.getParentScope());
	}
}
