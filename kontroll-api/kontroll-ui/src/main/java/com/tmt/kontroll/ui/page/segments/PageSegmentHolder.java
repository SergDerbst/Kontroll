package com.tmt.kontroll.ui.page.segments;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentScopeContext;

/**
 * <p>
 * The page segment holder holds all {@link PageSegmentScopeContext}s in a {@link TreeMap}
 * according to their scope name.
 *</p>
 *
 * @author SergDerbst
 *
 */
@Component
public class PageSegmentHolder {

	private final TreeMap<String, PageSegmentScopeContext>	segmentMap	= new TreeMap<>();

	public Set<PageSegment> fetchPageSegments(final String scopeName) throws ScopeNotFoundException {
		try {
			final PageSegmentScopeContext context = this.segmentMap.get(scopeName);
			return context != null ? context.getSegments() : new HashSet<PageSegment>();
		} catch (final Exception e) {
			throw ScopeNotFoundException.prepare(e, scopeName);
		}
	}

	public PageSegment fetchMatchingPageSegment(final String scopeName, final String requestPath) throws ScopeNotFoundException {
		for (final PageSegment segment : this.fetchPageSegments(scopeName)) {
			for (final String pattern : segment.getRequestPatterns()) {
				if (this.matchPageContext(pattern, requestPath)) {
					return segment;
				}
			}
		}
		return null;
	}

	public Collection<PageSegmentScopeContext> fetchAllContexts() {
		return this.segmentMap.values();
	}

	public void addPageSegment(final String scopeName, final PageSegment segment) {
		final PageSegmentScopeContext context = this.segmentMap.get(scopeName);
		if (context == null) {
			this.segmentMap.put(scopeName, this.createSegmentContext(segment, scopeName));
		} else {
			context.getSegments().add(segment);
		}
	}

	private PageSegmentScopeContext createSegmentContext(final PageSegment segment, final String scopeName) {
		final PageSegmentScopeContext segmentContext = new PageSegmentScopeContext(scopeName);
		segmentContext.getSegments().add(segment);
		return segmentContext;
	}

	private boolean matchPageContext(final String pattern, final String requestPath) {
		return Pattern.compile(pattern).matcher(requestPath).find(); // && this.matchConditions(pageContext);
	}

	private boolean matchConditions(final PageContext pageContext) {
		//TODO implement
		return true;
	}
}