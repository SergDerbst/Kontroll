package com.tmt.kontroll.content;

import java.util.List;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;

public class ContentContext {

	private final ContentDto	contentDTO;
	private final List<ScopedContentCondition> conditions;

	public ContentContext(final ContentDto contentDTO, 
	                      final List<ScopedContentCondition> conditions) {
		this.contentDTO = contentDTO;
		this.conditions = conditions;
	}

	public ContentDto getContentDTO() {
		return contentDTO;
	}

	public List<ScopedContentCondition> getConditions() {
		return conditions;
	}
}
