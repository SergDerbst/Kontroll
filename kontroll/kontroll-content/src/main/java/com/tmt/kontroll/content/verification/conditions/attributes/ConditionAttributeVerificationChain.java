package com.tmt.kontroll.content.verification.conditions.attributes;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;

@Component
public class ConditionAttributeVerificationChain {

	public boolean verify(List<ScopedContentConditionAttribute> scopedContentConditionAttributes, ConditionalOperator operator, ContentDto contentDto) {
		return false;
	}
}
