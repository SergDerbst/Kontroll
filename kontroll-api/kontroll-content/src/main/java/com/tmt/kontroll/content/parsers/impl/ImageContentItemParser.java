package com.tmt.kontroll.content.parsers.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.parsers.ContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Component
public class ImageContentItemParser implements ContentItemParser {

	@Override
	public ContentItem parse(final ScopedContentItem scopedContentItem) {
		return null;
	}
}
