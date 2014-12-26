package com.tmt.kontroll.content.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Component
public class ContentParser {

	@Autowired
	ContentParserProvider	provider;

	public List<ContentItem> parse(final Set<ScopedContentItem> items, final String parentId) throws NoContentParserFoundException {
		final List<ContentItem> parsed = new ArrayList<ContentItem>();
		for (final ScopedContentItem item : items) {
			final ContentItem contentItem = this.provider.provide(item);
			contentItem.setId(parentId + "." + item.getItemNumber());
			parsed.add(contentItem);
		}
		return parsed;
	}
}
