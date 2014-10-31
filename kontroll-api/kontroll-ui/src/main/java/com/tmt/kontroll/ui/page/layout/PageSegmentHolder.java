package com.tmt.kontroll.ui.page.layout;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.Page;
import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.events.Event;
import com.tmt.kontroll.ui.page.layout.navigator.PageNavigator;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentScopeContext;
import com.tmt.kontroll.ui.page.preparation.PageEventConfigurator;

/**
 * <p>
 * The page segment holder holds all {@link PageSegmentScopeContext}s in a {@link TreeMap}
 * according to their scope name.
 *</p>
 *<p>
 * When {@link PageSegment} are added, their configuration by annotations will be handled as well.
 * This includes:
 * <ul>
 * 	<li>creating a {@link PageSegmentScopeContext} and storing it under the respective scope name</li>
 * 	<li>handling the event configuration of all segments as configured by the {@link Event} annotation</li>
 * 	<li>handling the navigation configuration as configured by the {@link PageNavigator} annotation</li>
 * </ul>
 *</p>
 *<p>
 *<i>Note:</i> The navigation configuration is essentially a special "shorthand" form of event configuration.
 *</p>
 *
 * @author SergDerbst
 *
 */
@Component
public class PageSegmentHolder {

	private final TreeMap<String, PageSegmentScopeContext>	segmentMap	= new TreeMap<>();

	@Autowired
	PageHolder																							pageHolder;

	@Autowired
	PageEventConfigurator																		pageEventConfigurator;

	public void addPageSegment(final PageSegment segment) {
		segment.getClass().getAnnotations();
		final PageConfig pageConfig = segment.getClass().getAnnotation(PageConfig.class);
		for (final PageContext pageContext : pageConfig.contexts()) {
			this.handlePage(pageContext);
			this.handleSegmentContext(segment, pageContext);
			this.pageEventConfigurator.configure(segment, pageConfig, pageContext);
		}
	}

	private void handlePage(final PageContext pageContext) {
		final Page page = new Page();
		page.setScope("page");
		this.pageHolder.addPage(pageContext.pattern(), page);
	}

	private void handleSegmentContext(final PageSegment segment, final PageContext pageContext) {
		final String[] scopePath = pageContext.scope().split("\\.");
		segment.setParentScope(this.generateParentScopeName(scopePath));
		segment.setScope(scopePath[scopePath.length - 1]);
		final PageSegmentScopeContext segmentContext = this.segmentMap.get(pageContext.scope());
		if (segmentContext == null) {
			this.segmentMap.put(pageContext.scope(), this.createSegmentContext(segment, pageContext));
		}
	}

	private String generateParentScopeName(final String[] scopePath) {
		final StringBuilder s = new StringBuilder();
		for (int i = 0; i < scopePath.length - 1; i++) {
			s.append(scopePath[i]);
			if (i < scopePath.length - 2) {
				s.append(".");
			}
		}
		return s.toString();
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