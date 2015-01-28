package com.tmt.kontroll.content.parsers;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveField;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveFieldValue;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.updateField;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.exceptions.ContentParsingException;
import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

@Component
public class ContentParser {

	@Autowired
	ContentParserProvider	provider;

	public Set<ContentItem> parse(final Set<ScopedContentItem> items, final String parentId) throws NoContentParserFoundException {
		final Set<ContentItem> parsed = new TreeSet<>();
		for (final ScopedContentItem item : items) {
			final ContentItem contentItem = this.provider.provide(item);
			contentItem.setConditions(item.getConditions());
			contentItem.setContent(item.getContent());
			contentItem.setCss(item.getCss());
			contentItem.setDbId(item.getId());
			contentItem.setDomId(parentId + "." + item.getItemNumber());
			contentItem.setDbId(item.getId());
			contentItem.setItemNumber(item.getItemNumber());
			contentItem.setPreliminary(item.isPreliminary());
			contentItem.setType(item.getType());
			this.parseChildren(parentId, item.getChildItems());
			parsed.add(contentItem);
		}
		return parsed;
	}

	private void parseChildren(final String parentId, final List<ScopedContentItem> list) {
		if (list != null) {
			final Set<ScopedContentItem> items = new TreeSet<>();
			items.addAll(list);
			this.parse(items, parentId);
		}
	}

	public ScopedContentItem parse(final ContentItem contentItem, final ScopedContentItem scopedContentItem) {
		try {
			for (final Field field : retrievePropertyFields(ScopedContentItem.class)) {
				final Field contentItemField = retrieveField(field.getName(), contentItem);
				if (contentItemField != null) {
					updateField(scopedContentItem, retrieveFieldValue(contentItemField, contentItem), field);
				}
			}
			return scopedContentItem;
		} catch (final Exception e) {
			throw new ContentParsingException(e);
		}
	}
}
