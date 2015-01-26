package com.tmt.kontroll.ui.page.management.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.ContentService;
import com.tmt.kontroll.content.business.content.data.ContentLoadingContext;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Caption;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;

@Component
public class ContentLoader {

	@Autowired
	ContentService												contentService;

	@Autowired
	GlobalContext													globalContext;

	@Autowired
	PageSegmentChildrenAndContentAccessor	childrenAndContentHandler;

	public void load(final PageSegment segment, final String requestPath, final String scopeName, final String sessionId) {
		if (this.isReadyForContent(segment)) {
			this.childrenAndContentHandler.addContent(segment, this.contentService.loadContent(this.createContentLoadingContext(scopeName, requestPath)));
		}
	}

	private boolean isReadyForContent(final PageSegment pageSegment) {
		final Class<? extends PageSegment> segmentClass = pageSegment.getClass();
		return segmentClass.isAnnotationPresent(Content.class) && !segmentClass.isAnnotationPresent(Caption.class);
	}

	private ContentLoadingContext createContentLoadingContext(final String scopeName, final String requestPath) {
		return new ContentLoadingContext(this.globalContext.requestContextHolder().fetchRequestContext(requestPath), this.globalContext.globalContext(), requestPath, null, scopeName);
	}
}
