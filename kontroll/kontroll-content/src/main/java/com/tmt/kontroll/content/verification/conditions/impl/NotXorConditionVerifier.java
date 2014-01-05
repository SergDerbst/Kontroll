package com.tmt.kontroll.content.verification.conditions.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.content.verification.conditions.ConditionVerificationChain;
import com.tmt.kontroll.content.verification.conditions.ConditionVerifier;

@Component
public class NotXorConditionVerifier extends ConditionVerifier {

	@Autowired
	ConditionVerificationChain verificationChain;
	
	@Override
	protected boolean isResponsible(final ScopedContentCondition condition) {
		return ConditionalOperator.NotXor == condition.getOperator() && !condition.getChildConditions().isEmpty();
	}

	@Override
	protected boolean doVerify(final ScopedContentCondition condition, final ContentDto contentDto) {
		boolean check = false;
		for (final ScopedContentCondition childCondition : condition.getChildConditions()) {
			if (this.verificationChain.verify(childCondition, contentDto)) {
				if (check) {
					return true;
				}
				check = true;
			}
		}
		return !check;
	}
}
