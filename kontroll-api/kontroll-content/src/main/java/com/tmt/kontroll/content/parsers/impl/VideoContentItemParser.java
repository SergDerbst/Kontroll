package com.tmt.kontroll.content.parsers.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.ui.HtmlTag;
import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.parsers.ContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Component
public class VideoContentItemParser implements ContentItemParser<HtmlTag> {

	@Override
	public ContentItem<HtmlTag> parse(ScopedContentItem scopedContentItem) throws NoContentParserFoundException {
		return null;
	}
}
