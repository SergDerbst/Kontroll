package com.tmt.kontroll.ui.page.management;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.ui.exceptions.PageManagementException;
import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.layout.PageSegmentHolder;
import com.tmt.kontroll.ui.page.layout.caption.PageCaption;
import com.tmt.kontroll.ui.page.layout.caption.PageCaptionLoader;
import com.tmt.kontroll.ui.page.layout.content.PageContent;
import com.tmt.kontroll.ui.page.layout.content.PageContentLoader;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;

/**
 * <p>
 * The page manager is responsible for managing the application's pages during runtime. It loads contents
 * of the page based on the {@link PageContext}, i.e. session id, request path, page scope.
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
	PageContentLoader	pageContentLoader;

	@Autowired
	PageCaptionLoader	pageCaptionLoader;

	@Autowired
	PagePostProcessor	pagePostProcessor;

	public PageSegment manage(final String requestPath, final String scopeName, final String sessionId) throws PageManagementException {
		try {
			if (this.isPageLoad(scopeName)) {
				return this.pagePostProcessor.process(this.loadScope(this.pageHolder.fetchPageByPath(requestPath), requestPath, scopeName, sessionId));
			} else {
				return this.pagePostProcessor.process(this.loadScope(this.pageSegmentHolder.fetchPageSegments(scopeName), requestPath, scopeName, sessionId));
			}
		} catch (final Exception e) {
			throw new PageManagementException(e);
		}
	}

	private PageSegment loadScope(final List<PageSegment> pageSegments, final String requestPath, final String scopeName, final String sessionId) throws ContentException, ScopeNotFoundException {
		for (final PageSegment segment : pageSegments) {
			for (final PageContext pageContext : segment.getClass().getAnnotation(PageConfig.class).contexts()) {
				if (this.matchPageContext(pageContext, requestPath)) {
					return this.loadScope(segment, requestPath, scopeName, sessionId);
				}
			}
		}
		throw ScopeNotFoundException.prepare(null, scopeName);
	}

	private PageSegment loadScope(final PageSegment segment, final String requestPath, final String scopeName, final String sessionId) throws ContentException {
		this.handleCaption(segment, scopeName, sessionId);
		this.handleContent(segment, requestPath, scopeName, sessionId);
		this.handleChildren(segment, requestPath, sessionId);
		return segment;
	}

	private void handleCaption(final PageSegment segment, final String scopeName, final String sessionId) {
		final PageCaption pageCaption = segment.getClass().getAnnotation(PageCaption.class);
		if (pageCaption != null) {
			segment.setCaption(this.pageCaptionLoader.load(scopeName, pageCaption.value(), sessionId));
		}
	}

	private void handleContent(final PageSegment segment, final String requestPath, final String scopeName, final String sessionId) {
		if (segment.getClass().isAnnotationPresent(PageContent.class)) {
			this.pageContentLoader.load(segment, requestPath, scopeName, sessionId);
		}
	}

	private void handleChildren(final PageSegment segment, final String requestPath, final String sessionId) throws ContentException {
		for (final PageSegment childSegment : segment.getChildren().values()) {
			this.loadScope(childSegment, requestPath, childSegment.getParentScope() + "." + childSegment.getScope(), sessionId);
		}
	}

	private boolean matchPageContext(final PageContext pageContext, final String requestPath) {
		return Pattern.compile(pageContext.pattern()).matcher(requestPath).find() && this.matchConditions(pageContext);
	}

	private boolean matchConditions(final PageContext pageContext) {
		//TODO implement
		return true;
	}

	private boolean isPageLoad(final String scopeName) {
		return "page".equals(scopeName);
	}
}