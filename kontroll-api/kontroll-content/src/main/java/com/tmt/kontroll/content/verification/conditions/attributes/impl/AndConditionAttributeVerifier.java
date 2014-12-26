package com.tmt.kontroll.content.verification.conditions.attributes.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.data.ContentOperatingContext;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.ConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerificationChain;

@Component
public class AndConditionAttributeVerifier extends ConditionAttributeVerifier {

	@Autowired
	ConditionAttributeValueVerificationChain verificationChain;
	
	@Override
	protected boolean isResponsible(final ConditionalOperator operator) {
		return ConditionalOperator.And == operator;
	}

	@Override
	protected boolean doVerify(final List<ScopedContentConditionAttribute> attributes, final ContentOperatingContext contentDto) {
		for (final ScopedContentConditionAttribute attribute : attributes) {
			if (!this.verificationChain.verify(attribute, contentDto)) {
				return false;
			}
		}
		return true;
	}
}
