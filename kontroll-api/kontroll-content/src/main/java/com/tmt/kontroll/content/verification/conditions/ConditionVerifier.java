package com.tmt.kontroll.content.verification.conditions;

import com.tmt.kontroll.content.business.content.data.in.ContentLoadingContext;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;

public abstract class ConditionVerifier {

	private ConditionVerifier nextVerifier;
	
	protected abstract boolean isResponsible(final ScopedContentCondition condition);
	
	protected abstract boolean doVerify(final ScopedContentCondition condition, final ContentLoadingContext contentDto);

	public boolean verify(final ScopedContentCondition condition, final ContentLoadingContext contentDto) {
		if (isResponsible(condition)) {
			return this.doVerify(condition, contentDto);
		}
		if (this.getNextVerifier() == null) {
			throw new RuntimeException("No condition verifier found for: " + condition.getName());
		}
		return this.getNextVerifier().verify(condition, contentDto);
	}
	
	protected ConditionVerifier getNextVerifier() {
		return nextVerifier;
	}

	public void setNextVerifier(final ConditionVerifier nextVerifier) {
		this.nextVerifier = nextVerifier;
	}
}
