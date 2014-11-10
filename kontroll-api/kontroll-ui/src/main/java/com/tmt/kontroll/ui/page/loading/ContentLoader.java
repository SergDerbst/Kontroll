package com.tmt.kontroll.ui.page.loading;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.business.content.ContentDto;
import com.tmt.kontroll.content.business.content.ContentService;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Caption;
import com.tmt.kontroll.ui.page.configuration.annotations.content.Content;

@Component
public class ContentLoader {

	@Autowired
	ContentService	contentService;

	@Autowired
	GlobalContext		globalContext;

	public void load(final PageSegment pageSegment, final String requestPath, final String scopeName, final String sessionId) {
		if (this.isReadyForContent(pageSegment)) {
			List<ContentItem> content = null;
			final ContentDto contentDto = this.createContentDto(requestPath, scopeName);
			content = this.contentService.loadContent(contentDto);
			pageSegment.setContent(content);
		}
	}

	private boolean isReadyForContent(final PageSegment pageSegment) {
		final Class<? extends PageSegment> segmentClass = pageSegment.getClass();
		return !segmentClass.isAnnotationPresent(Caption.class) && segmentClass.isAnnotationPresent(Content.class);
	}

	private ContentDto createContentDto(final String requestPath, final String scopeName) {
		return new ContentDto(this.globalContext.requestContextHolder().fetchRequestContext(requestPath), this.globalContext.globalContext(), requestPath, scopeName);
	}
}
