package com.tmt.kontroll.ui.page.loading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;
import com.tmt.kontroll.context.global.GlobalContext;

@Component
public class CaptionLoader {

	private static final Logger	Log	= LoggerFactory.getLogger(ContentLoader.class);

	@Autowired
	CaptionDaoService						captionService;

	@Autowired
	GlobalContext								globalContext;

	public ContentItem load(final String scopeName, final String identifier, final String sessionId) {
		Caption caption = null;
		try {
			caption = this.captionService.findByIdentifierAndLocale(identifier, this.globalContext.sessionContextHolder().sessionContext(sessionId).getLocale());
		} catch (final Exception e) {
			Log.info("Exception thrown while loading caption: {0}", e.getMessage());
			Log.info("Loading caption failed. Loading default caption instead.");
		} finally {
			if (caption == null) {
				return this.deliverDefaultCaption(scopeName, identifier);
			}
		}
		return this.makeCaptionContent(caption);
	}

	private ContentItem makeCaptionContent(final Caption caption) {
		final ContentItem contentItem = new ContentItem();
		contentItem.setContent(caption.getText());
		contentItem.setId(caption.getIdentifier());
		return contentItem;
	}

	private ContentItem deliverDefaultCaption(final String scopeName, final String identifier) {
		final String[] scopePath = scopeName.split("\\.");
		final ContentItem caption = new ContentItem();
		caption.setId(identifier + ".caption");
		caption.setContent(scopePath[scopePath.length - 1]);
		return caption;
	}
}
