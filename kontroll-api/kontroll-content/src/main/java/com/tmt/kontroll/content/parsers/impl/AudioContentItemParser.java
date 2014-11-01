package com.tmt.kontroll.content.parsers.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.parsers.ContentItemParser;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Component
public class AudioContentItemParser implements ContentItemParser {

	@Override
	public ContentItem parse(final ScopedContentItem scopedContentItem) throws NoContentParserFoundException {
		return null;
	}
}
