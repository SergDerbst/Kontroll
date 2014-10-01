package com.tmt.kontroll.ui.page.management.contexts;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.ui.page.layout.PageSegment;

public class PageSegmentScopeContext {

	private final String						scopeName;
	private final List<PageSegment>	segments	= new ArrayList<>();

	//TODO: conditions

	public PageSegmentScopeContext(final String scopeName) {
		this.scopeName = scopeName;
	}

	public String getScopeName() {
		return this.scopeName;
	}

	public List<PageSegment> getSegments() {
		return this.segments;
	}
}
