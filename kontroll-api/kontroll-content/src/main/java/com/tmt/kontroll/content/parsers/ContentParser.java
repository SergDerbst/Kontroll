package com.tmt.kontroll.content.parsers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Component
public class ContentParser {

	@Autowired
	ContentParserProvider provider;

	public List<ContentItem<? extends Enum<?>>> parse(final List<ScopedContentItem> items) throws NoContentParserFoundException {
		final List<ContentItem<? extends Enum<?>>> parsed = new ArrayList<ContentItem<? extends Enum<?>>>();
		for (final ScopedContentItem item : items) {
			parsed.add(this.provider.provide(item));
		}
		return parsed;
	}
}
