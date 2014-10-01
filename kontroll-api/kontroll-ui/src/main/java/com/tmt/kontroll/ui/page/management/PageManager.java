package com.tmt.kontroll.ui.page.management;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.ContentService;
import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.ui.exceptions.PageManagementException;
import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.layout.impl.PageContent;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;

/**
 * <p>
 * The page manager is responsible for managing the application's pages during runtime. It loads contents
 * of the page based on the {@link PageContext}, i.e. request path and page scope, given by an incoming request.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class PageManager {

	@Autowired
	PageHolder				pageHolder;

	@Autowired
	PageSegmentHolder	pageSegmentHolder;

	@Autowired
	GlobalContext			globalContext;

	@Autowired
	ContentService		contentService;

	public PageSegment manage(final String requestPath, final String scopeName) throws PageManagementException {
		try {
			if (this.isPageLoad(scopeName)) {
				return this.loadScope(this.pageHolder.fetchPageByPath(requestPath), requestPath, scopeName);
			} else {
				return this.loadScope(this.pageSegmentHolder.fetchPageSegments(scopeName), requestPath, scopeName);
			}
		} catch (final Exception e) {
			throw new PageManagementException(e);
		}
	}

	private PageSegment loadScope(final List<PageSegment> pageSegments, final String requestPath, final String scopeName) throws ContentException, ScopeNotFoundException {
		for (final PageSegment segment : pageSegments) {
			for (final PageContext pageContext : segment.getClass().getAnnotation(PageConfig.class).contexts()) {
				if (this.matchPageContext(pageContext, requestPath)) {
					return this.loadScope(segment, requestPath, scopeName);
				}
			}
		}
		throw ScopeNotFoundException.prepare(null, scopeName);
	}

	private PageSegment loadScope(final PageSegment segment, final String requestPath, final String scopeName) throws ContentException {
		if (segment instanceof PageContent) {
			((PageContent) segment).setContent(this.contentService.loadContent(this.createContentDto(requestPath, scopeName)));
		}
		for (final PageSegment childSegment : segment.getChildren().values()) {
			this.loadScope(childSegment, requestPath, scopeName + "." + childSegment.getScopeName());
		}
		return segment;
	}

	private boolean matchPageContext(final PageContext pageContext, final String requestPath) {
		return Pattern.compile(pageContext.pattern()).matcher(requestPath).find() && this.matchConditions(pageContext);
	}

	private boolean matchConditions(final PageContext pageContext) {
		//TODO implement
		return true;
	}

	private ContentDto createContentDto(final String requestPath, final String scopeName) {
		return new ContentDto(this.globalContext.fetchRequestContext(requestPath), this.globalContext.getGlobalContextDto(), requestPath, scopeName);
	}

	private boolean isPageLoad(final String scopeName) {
		return "page".equals(scopeName);
	}
}