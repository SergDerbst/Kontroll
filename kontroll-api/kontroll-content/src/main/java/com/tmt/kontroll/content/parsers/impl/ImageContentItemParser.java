package com.tmt.kontroll.content.parsers.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.parsers.ContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.content.persistence.selections.HtmlTag;

@Component
public class ImageContentItemParser implements ContentItemParser<HtmlTag> {

	@Override
	public ContentItem<HtmlTag> parse(ScopedContentItem scopedContentItem) {
		return null;
	}
}
