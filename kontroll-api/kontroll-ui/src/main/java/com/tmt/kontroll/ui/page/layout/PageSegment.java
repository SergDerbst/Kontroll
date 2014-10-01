package com.tmt.kontroll.ui.page.layout;

import java.util.TreeMap;

import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;

public abstract class PageSegment implements PageScope {

	private final TreeMap<PageSegmentOrdinalKey, PageSegment>	children	= new TreeMap<>();

	public TreeMap<PageSegmentOrdinalKey, PageSegment> getChildren() {
		return this.children;
	}

	public boolean hasChildren() {
		return this.children.size() > 0;
	}
}
