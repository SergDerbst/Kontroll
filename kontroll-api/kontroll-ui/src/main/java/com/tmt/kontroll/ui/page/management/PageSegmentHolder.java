package com.tmt.kontroll.ui.page.management;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.Page;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;
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

	@Autowired
	PageHolder																							pageHolder;

	private final TreeMap<String, PageSegmentScopeContext>	segmentMap	= new TreeMap<>();

	public void addPageSegment(final PageSegment segment) {
		segment.getClass().getAnnotations();
		final PageConfig pageConfig = segment.getClass().getAnnotation(PageConfig.class);
		for (final PageContext pageContext : pageConfig.contexts()) {
			this.handlePage(pageContext);
			this.handleSegment(segment, pageContext);
		}
	}

	private void handlePage(final PageContext pageContext) {
		this.pageHolder.addPage(pageContext.pattern(), new Page());
	}

	private void handleSegment(final PageSegment segment, final PageContext pageContext) {
		final PageSegmentScopeContext segmentContext = this.segmentMap.get(pageContext.scope());
		if (segmentContext == null) {
			this.segmentMap.put(pageContext.scope(), this.createSegmentContext(segment, pageContext));
		}
	}

	private PageSegmentScopeContext createSegmentContext(final PageSegment segment, final PageContext pageContext) {
		final PageSegmentScopeContext segmentContext = new PageSegmentScopeContext(pageContext.scope());
		segmentContext.getSegments().add(segment);
		return segmentContext;
	}

	public List<PageSegment> fetchPageSegments(final String scopeName) throws ScopeNotFoundException {
		try {
			return this.segmentMap.get(scopeName).getSegments();
		} catch (final Exception e) {
			throw ScopeNotFoundException.prepare(e, scopeName);
		}
	}

	public Collection<PageSegmentScopeContext> fetchAllContexts() {
		return this.segmentMap.values();
	}
}