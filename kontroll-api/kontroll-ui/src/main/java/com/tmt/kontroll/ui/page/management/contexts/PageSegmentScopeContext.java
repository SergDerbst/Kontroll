package com.tmt.kontroll.ui.page.management.contexts;

import java.util.HashSet;
import java.util.Set;

import com.tmt.kontroll.ui.page.segments.PageSegment;

public class PageSegmentScopeContext {

	private final String						scopeName;
	private final Set<PageSegment>	segments	= new HashSet<>();

	//TODO: conditions

	public PageSegmentScopeContext(final String scopeName) {
		this.scopeName = scopeName;
	}

	public String getScopeName() {
		return this.scopeName;
	}

	public Set<PageSegment> getSegments() {
		return this.segments;
	}
}
