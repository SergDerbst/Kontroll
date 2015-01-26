package com.tmt.kontroll.ui.page.management.content;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.content.persistence.services.CaptionDaoService;
import com.tmt.kontroll.context.global.GlobalContext;

/**
 * Loads a caption for the given identifier and the {@link Locale} stored in the session identified
 * by the given session id. If no caption can be found for this locale, the default caption will
 * be loaded ({@link Locale#US}).
 *
 * @author SergDerbst
 *
 */
@Component
public class CaptionLoader {

	@Autowired
	CaptionDaoService	captionService;

	@Autowired
	GlobalContext			globalContext;

	public ContentItem load(final String identifier, final String sessionId) {
		Caption caption = this.captionService.findByIdentifierAndLocale(identifier, this.globalContext.sessionContextHolder().sessionContext(sessionId).getLocale());
		if (caption == null) {
			caption = this.captionService.findByIdentifierAndLocale(identifier, Locale.US);
		}
		return this.makeCaptionContent(caption);
	}

	private ContentItem makeCaptionContent(final Caption caption) {
		final ContentItem contentItem = new ContentItem();
		contentItem.setContent(caption.getText());
		contentItem.setDomId(caption.getIdentifier() + ".caption");
		return contentItem;
	}
}
