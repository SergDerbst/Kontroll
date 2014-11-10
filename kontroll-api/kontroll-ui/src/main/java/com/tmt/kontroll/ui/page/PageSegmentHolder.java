package com.tmt.kontroll.ui.page;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
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
}