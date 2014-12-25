package com.tmt.kontroll.ui.page.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.business.content.ContentLoadingService;
import com.tmt.kontroll.content.business.content.data.ContentLoadingContext;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Caption;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;

@Component
public class ContentLoader {

	@Autowired
	ContentLoadingService									contentLoadingService;

	@Autowired
	GlobalContext													globalContext;

	@Autowired
	PageSegmentChildrenAndContentAccessor	childrenAndContentHandler;

	public void load(final PageSegment pageSegment, final String requestPath, final String scopeName, final String sessionId) {
		if (this.isReadyForContent(pageSegment)) {
			List<ContentItem> content = null;
			final ContentLoadingContext contentLoadingContext = this.createContentLoadingContext(requestPath, scopeName);
			content = this.contentLoadingService.loadContent(contentLoadingContext);
			this.childrenAndContentHandler.addContent(pageSegment, content);
		}
	}

	private boolean isReadyForContent(final PageSegment pageSegment) {
		final Class<? extends PageSegment> segmentClass = pageSegment.getClass();
		return !segmentClass.isAnnotationPresent(Caption.class) && segmentClass.isAnnotationPresent(Content.class);
	}

	private ContentLoadingContext createContentLoadingContext(final String requestPath, final String scopeName) {
		return new ContentLoadingContext(this.globalContext.requestContextHolder().fetchRequestContext(requestPath), this.globalContext.globalContext(), requestPath, scopeName);
	}
}
