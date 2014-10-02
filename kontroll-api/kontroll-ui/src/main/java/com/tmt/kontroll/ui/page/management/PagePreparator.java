package com.tmt.kontroll.ui.page.management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;
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
		final List<PageSegment> segments = scopeContext.getSegments();
		for (final PageSegment segment : segments) {
			for (final PageContext pageContext : segment.getClass().getAnnotation(PageConfig.class).contexts()) {
				this.addSegmentToParentScope(scopeContext, pageContext, segment);
			}
		}
	}

	private void addSegmentToParentScope(final PageSegmentScopeContext scopeContext, final PageContext pageContext, final PageSegment segment) throws ScopeNotFoundException {
		if (this.isRootScope(scopeContext)) {
			this.pageHolder.fetchPageByPattern(pageContext.pattern()).getChildren().put(new PageSegmentOrdinalKey(pageContext.ordinal(), pageContext.conditions(), pageContext.operator()), segment);
		} else {
			for (final PageSegment parentSegment : this.pageSegmentHolder.fetchPageSegments(this.determineParentScopeName(scopeContext.getScopeName().split("\\.")))) {
				parentSegment.getChildren().put(new PageSegmentOrdinalKey(pageContext.ordinal(), pageContext.conditions(), pageContext.operator()), segment);
			}
		}
	}

	private String determineParentScopeName(final String[] scopePath) {
		String parentScopeName = "";
		for (int i = 0; i < scopePath.length - 1; i++) {
			parentScopeName = parentScopeName + scopePath[i];
			if (i != scopePath.length - 2) {
				parentScopeName = parentScopeName + ".";
			}
		}
		return parentScopeName;
	}

	private boolean isRootScope(final PageSegmentScopeContext scopeContext) {
		return scopeContext.getScopeName().endsWith("header") || scopeContext.getScopeName().endsWith("body") || scopeContext.getScopeName().endsWith("footer");
	}
}
