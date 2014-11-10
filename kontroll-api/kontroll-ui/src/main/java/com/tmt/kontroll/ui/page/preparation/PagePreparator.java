package com.tmt.kontroll.ui.page.preparation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.PageSegmentHolder;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
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
			for (final PageContext pageContext : segment.getClass().getAnnotation(PageConfig.class).contexts()) {
				this.addSegmentToParentScope(scopeContext, pageContext, segment);
			}
		}
	}

	private void addSegmentToParentScope(final PageSegmentScopeContext scopeContext, final PageContext pageContext, final PageSegment segment) throws ScopeNotFoundException {
		final String segmentScope = this.fetchSegmentScope(pageContext.scope());
		if (this.isRootScope(scopeContext)) {
			this.pageHolder.fetchPageByPattern(pageContext.pattern()).getChildren().put(new PageSegmentOrdinalKey(pageContext.ordinal(), segmentScope, pageContext.conditions(), pageContext.operator()), segment);
		} else {
			for (final PageSegment parentSegment : this.pageSegmentHolder.fetchPageSegments(this.determineParentScopeName(scopeContext.getScopeName().split("\\.")))) {
				parentSegment.getChildren().put(new PageSegmentOrdinalKey(pageContext.ordinal(), segmentScope, pageContext.conditions(), pageContext.operator()), segment);
			}
		}
	}

	private String fetchSegmentScope(final String scope) {
		final String[] scopePath = scope.split("\\.");
		return scopePath[scopePath.length - 1];
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
		final String scopeName = scopeContext.getScopeName();
		return scopeName.split("\\.").length == 2 && scopeName.startsWith("page.");
	}
}
