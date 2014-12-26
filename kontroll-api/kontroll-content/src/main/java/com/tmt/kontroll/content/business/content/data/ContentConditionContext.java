package com.tmt.kontroll.content.business.content.data;

import java.util.List;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;

public class ContentConditionContext {

	private final ContentOperatingContext	contentDTO;
	private final List<ScopedContentCondition> conditions;

	public ContentConditionContext(final ContentOperatingContext contentDTO, 
	                      final List<ScopedContentCondition> conditions) {
		this.contentDTO = contentDTO;
		this.conditions = conditions;
	}

	public ContentOperatingContext getContentDTO() {
		return contentDTO;
	}

	public List<ScopedContentCondition> getConditions() {
		return conditions;
	}
}
