package com.tmt.kontroll.content.parsers.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.parsers.ContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.context.ui.HtmlTag;

@Component
public class ImageContentItemParser implements ContentItemParser {

	@Override
	public ContentItem parse(final ScopedContentItem scopedContentItem) {
		final ContentItem contentItem = new ContentItem(HtmlTag.Image);
		contentItem.setContent(scopedContentItem.getContent());
		contentItem.setCssClass(scopedContentItem.getCssClass());
		return contentItem;
	}
}
