package com.tmt.kontroll.content.parsers;

import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

public interface ContentItemParser<T extends Enum<T>> {

	ContentItem<T> parse(ScopedContentItem scopedContentItem) throws NoContentParserFoundException;
}
