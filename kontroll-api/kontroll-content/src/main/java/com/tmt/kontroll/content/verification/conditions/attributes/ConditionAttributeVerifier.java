package com.tmt.kontroll.content.verification.conditions.attributes;

import java.util.List;

import com.tmt.kontroll.content.business.content.data.ContentOperatingContext;
import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;
import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;

public abstract class ConditionAttributeVerifier {

private ConditionAttributeVerifier nextVerifier;
	
	protected abstract boolean isResponsible(ConditionalOperator operator);
	
	protected abstract boolean doVerify(final List<ScopedContentConditionAttribute> attributes, final ContentOperatingContext contentDto);

	public boolean verify(final List<ScopedContentConditionAttribute> attributes, 
	                      final ConditionalOperator operator,
	                      final ContentOperatingContext contentDto) {
		if (isResponsible(operator)) {
			return this.doVerify(attributes, contentDto);
		}
		if (this.getNextVerifier() == null) {
			throw new RuntimeException("No condition verifier found for operator: " + operator.toString());
		}
		return this.getNextVerifier().verify(attributes, operator, contentDto);
	}
	
	protected ConditionAttributeVerifier getNextVerifier() {
		return nextVerifier;
	}

	public void setNextVerifier(final ConditionAttributeVerifier nextVerifier) {
		this.nextVerifier = nextVerifier;
	}
}
