package com.tmt.kontroll.content.verification.conditions.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.verification.conditions.ConditionVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.ConditionAttributeVerificationChain;

@Component
public class AttributesConditionVerifier extends ConditionVerifier {

	@Autowired
	ConditionAttributeVerificationChain verificationChain;
	
	@Override
	protected boolean isResponsible(ScopedContentCondition condition) {
		return condition.getChildConditions().isEmpty();
	}

	@Override
	protected boolean doVerify(final ScopedContentCondition condition, final ContentDto contentDto) {
		return this.verificationChain.verify(condition.getConditionAttributes(), condition.getOperator(), contentDto);
	}
}
