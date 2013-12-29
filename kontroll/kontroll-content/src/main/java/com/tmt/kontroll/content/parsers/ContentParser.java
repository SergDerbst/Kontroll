package com.tmt.kontroll.content.parsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.exceptions.NoContentParserFoundException;
import com.tmt.kontroll.content.items.ContentItem;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;

@Component
public class ContentParser {

	@Autowired
	ContentParserProvider provider;

	public List<ContentItem<? extends Enum<?>>> parse(ScopedContent scopedContent) throws NoContentParserFoundException {
		return null;
	}
}
