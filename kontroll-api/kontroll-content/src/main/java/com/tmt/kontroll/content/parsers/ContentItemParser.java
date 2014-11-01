package com.tmt.kontroll.content.parsers;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

public interface ContentItemParser {

	ContentItem parse(ScopedContentItem scopedContentItem) throws NoContentParserFoundException;
}
