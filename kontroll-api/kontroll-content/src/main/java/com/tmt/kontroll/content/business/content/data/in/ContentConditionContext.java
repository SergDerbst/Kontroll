package com.tmt.kontroll.content.business.content.data.in;

import java.util.List;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;

public class ContentConditionContext {

	private final ContentLoadingContext	contentDTO;
	private final List<ScopedContentCondition> conditions;

	public ContentConditionContext(final ContentLoadingContext contentDTO, 
	                      final List<ScopedContentCondition> conditions) {
		this.contentDTO = contentDTO;
		this.conditions = conditions;
	}

	public ContentLoadingContext getContentDTO() {
		return contentDTO;
	}

	public List<ScopedContentCondition> getConditions() {
		return conditions;
	}
}
