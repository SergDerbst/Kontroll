package com.tmt.kontroll.content.verification.conditions.attributes.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.data.ContentLoadingContext;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.ConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerificationChain;

@Component
public class NotAndConditionAttributeVerifier extends ConditionAttributeVerifier {

	@Autowired
	ConditionAttributeValueVerificationChain verificationChain;
	
	@Override
	protected boolean isResponsible(final ConditionalOperator operator) {
		return ConditionalOperator.NotAnd == operator;
	}

	@Override
	protected boolean doVerify(final List<ScopedContentConditionAttribute> attributes, final ContentLoadingContext contentDto) {
		boolean check = true;
		for (final ScopedContentConditionAttribute attribute : attributes) {
			if (!this.verificationChain.verify(attribute, contentDto)) {
				check = false;
			}
		}
		return !check;
	}
}
