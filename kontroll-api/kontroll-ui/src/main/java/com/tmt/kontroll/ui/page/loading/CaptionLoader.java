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
		caption = this.captionService.findByIdentifierAndLocale(identifier, this.globalContext.sessionContextHolder().sessionContext(sessionId).getLocale());
		return this.makeCaptionContent(caption);
	}

	private ContentItem makeCaptionContent(final Caption caption) {
		final ContentItem contentItem = new ContentItem();
		contentItem.setContent(caption.getText());
		contentItem.setId(caption.getIdentifier() + ".caption");
		return contentItem;
	}
}
