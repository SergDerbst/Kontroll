package com.tmt.kontroll.ui.page.management;

import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.exceptions.ContentException;
import com.tmt.kontroll.ui.exceptions.PageManagementException;
import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.PageSegmentHolder;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Caption;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.loading.CaptionLoader;
import com.tmt.kontroll.ui.page.loading.ContentLoader;

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
	PageHolder									pageHolder;

	@Autowired
	PageSegmentHolder						pageSegmentHolder;

	@Autowired
	ContentLoader								pageContentLoader;

	@Autowired
	CaptionLoader								pageCaptionLoader;

	@Autowired
	PageManagementPostProcessor	pagePostProcessor;

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

	private PageSegment loadScope(final Set<PageSegment> pageSegments, final String requestPath, final String scopeName, final String sessionId) throws ContentException, ScopeNotFoundException {
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
		final Caption pageCaption = segment.getClass().getAnnotation(Caption.class);
		if (pageCaption != null) {
			segment.setCaption(this.pageCaptionLoader.load(scopeName, pageCaption.value(), sessionId));
		}
	}

	private void handleContent(final PageSegment segment, final String requestPath, final String scopeName, final String sessionId) {
		if (segment.getClass().isAnnotationPresent(Content.class)) {
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