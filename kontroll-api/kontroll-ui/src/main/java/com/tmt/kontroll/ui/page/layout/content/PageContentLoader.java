package com.tmt.kontroll.ui.page.layout.content;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.business.content.ContentDto;
import com.tmt.kontroll.content.business.content.ContentService;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.layout.caption.PageCaption;

@Component
public class PageContentLoader {

	private static final Logger	Log	= LoggerFactory.getLogger(PageContentLoader.class);

	@Autowired
	ContentService							contentService;

	@Autowired
	GlobalContext								globalContext;

	public void load(final PageSegment pageSegment, final String requestPath, final String scopeName, final String sessionId) {
		if (this.isReadyForContent(pageSegment)) {
			List<ContentItem> content = null;
			final ContentDto contentDto = this.createContentDto(requestPath, scopeName);
			try {
				content = this.contentService.loadContent(contentDto);
			} catch (final Exception e) {
				Log.info("Exception thrown while loading content: {0}", e.getMessage());
				Log.info("Loading content failed. Loading default content instead.");
			} finally {
				if (content == null) {
					content = this.deliverDefaultContent(contentDto);
				}
				pageSegment.setContent(content);
			}
		}
	}

	@SuppressWarnings("serial")
	private List<ContentItem> deliverDefaultContent(final ContentDto scopeDto) {
		return new ArrayList<ContentItem>() {
			{
				final ContentItem contentItem = new ContentItem();
				contentItem.setContent(scopeDto.getScopeName());
				contentItem.setId(scopeDto.getScopeName());
				this.add(contentItem);
			}
		};
	}

	private boolean isReadyForContent(final PageSegment pageSegment) {
		final Class<? extends PageSegment> segmentClass = pageSegment.getClass();
		return !segmentClass.isAnnotationPresent(PageCaption.class) && segmentClass.isAnnotationPresent(PageContent.class);
	}

	private ContentDto createContentDto(final String requestPath, final String scopeName) {
		return new ContentDto(this.globalContext.requestContextHolder().fetchRequestContext(requestPath), this.globalContext.globalContext(), requestPath, scopeName);
	}
}
