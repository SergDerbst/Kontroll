package com.tmt.kontroll.content.verification.conditions.attributes.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.ConditionAttributeVerifier;
import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerificationChain;

@Component
public class XorConditionAttributeVerifier extends ConditionAttributeVerifier {

	@Autowired
	ConditionAttributeValueVerificationChain verificationChain;
	
	@Override
	protected boolean isResponsible(final ConditionalOperator operator) {
		return ConditionalOperator.Xor == operator;
	}

	@Override
	protected boolean doVerify(final List<ScopedContentConditionAttribute> attributes, final ContentDto contentDto) {
		boolean check = false;
		for (final ScopedContentConditionAttribute attribute : attributes) {
			if (this.verificationChain.verify(attribute, contentDto)) {
				if (check) {
					return false;
				}
				check = true;
			}
		}
		return check;
	}
}