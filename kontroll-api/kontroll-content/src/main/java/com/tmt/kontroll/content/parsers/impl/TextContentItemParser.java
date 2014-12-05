package com.tmt.kontroll.content.parsers.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.parsers.ContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.context.ui.HtmlTag;

@Component
public class TextContentItemParser implements ContentItemParser {

	@Override
	public ContentItem parse(final ScopedContentItem scopedContentItem) {
		final ContentItem item = new ContentItem(this.determineTag(scopedContentItem));
		item.setContent(scopedContentItem.getContent());
		item.setCssClass(scopedContentItem.getCssClass());
		return item;
	}

	private HtmlTag determineTag(final ScopedContentItem scopedContentItem) {
		final HtmlTag tag = HtmlTag.forTagName(scopedContentItem.getTagName());
		return tag == null ? HtmlTag.Div : tag;
	}
}
