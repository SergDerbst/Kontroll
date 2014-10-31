package com.tmt.kontroll.ui.page.layout.caption;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.caption.CaptionDto;
import com.tmt.kontroll.content.business.caption.CaptionService;
import com.tmt.kontroll.content.items.impl.CaptionContentItem;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.ui.page.layout.content.PageContentLoader;

@Component
public class PageCaptionLoader {

	private static final Logger	Log	= LoggerFactory.getLogger(PageContentLoader.class);

	@Autowired
	CaptionService							captionService;

	@Autowired
	GlobalContext								globalContext;

	public CaptionContentItem load(final String scopeName, final String identifier) {
		CaptionContentItem caption = null;
		final CaptionDto captionDto = this.createCaptionDto(identifier);
		try {
			caption = this.captionService.loadCaption(captionDto);
		} catch (final Exception e) {
			Log.info("Exception thrown while loading caption: {0}", e.getMessage());
			Log.info("Loading caption failed. Loading default caption instead.");
		} finally {
			if (caption == null) {
				caption = this.deliverDefaultCaption(scopeName, identifier);
			}
		}
		return caption;
	}

	private CaptionContentItem deliverDefaultCaption(final String scopeName, final String identifier) {
		final String[] scopePath = scopeName.split("\\.");
		final CaptionContentItem caption = new CaptionContentItem();
		caption.setId(identifier);
		caption.setContent(scopePath[scopePath.length - 1]);
		return caption;
	}

	private CaptionDto createCaptionDto(final String identifier) {
		return new CaptionDto(Locale.ENGLISH, identifier);
	}
}
