package com.tmt.kontroll.ui.page;

import java.util.regex.Pattern;

import com.tmt.kontroll.commons.utils.RegexPatternComparator;

public class PageContentContext implements Comparable<PageContentContext> {
	
	private final RegexPatternComparator patternComparator = new RegexPatternComparator();

	private final Pattern requestContextPathPattern;
	private final String scopeName;
	
	public PageContentContext(final Pattern requestContextPathPattern,
	                          final String scopeName) {
		this.requestContextPathPattern = requestContextPathPattern;
		this.scopeName = scopeName;
	}

	public Pattern getRequestContextPathPattern() {
		return requestContextPathPattern;
	}

	public String getScopeName() {
		return scopeName;
	}

	@Override
	public int compareTo(PageContentContext other) {
		int patternComparison = this.patternComparator.compare(this.requestContextPathPattern, other.getRequestContextPathPattern());
		if (patternComparison == 0) {
			return this.scopeName.compareTo(other.getScopeName());
		}
		return patternComparison;
	}
}
