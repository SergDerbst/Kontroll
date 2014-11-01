package com.tmt.kontroll.content.parsers.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.parsers.ContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Component
public class TextContentItemParser implements ContentItemParser {

	@Override
	public ContentItem parse(ScopedContentItem scopedContentItem) {
		return null;
	}
}
