package com.tmt.kontroll.content.parsers.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.parsers.ContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Component
public class TextContentItemParser implements ContentItemParser<HtmlTag> {

	@Override
	public ContentItem<HtmlTag> parse(ScopedContentItem scopedContentItem) {
		return null;
	}
}
