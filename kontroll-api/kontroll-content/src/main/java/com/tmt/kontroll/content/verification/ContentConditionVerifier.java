package com.tmt.kontroll.content.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.business.content.data.in.ContentConditionContext;
import com.tmt.kontroll.content.business.content.data.in.ContentLoadingContext;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.verification.conditions.ConditionInconsistentException;
import com.tmt.kontroll.content.verification.conditions.ConditionVerificationChain;

/**
 * The actual component matching given application values against conditions in
 * order to deliver content.
 * 
 * @author Sergio Weigel
 * 
 */
@Component
public class ContentConditionVerifier {

	@Autowired
	ConditionVerificationChain verificationChain;

	public <V, C extends ContentConditionContext> boolean verify(final ScopedContentCondition condition, final ContentLoadingContext contentDto) {
		this.assertConsistent(condition);
		return this.verificationChain.verify(condition, contentDto);
	}

	private void assertConsistent(final ScopedContentCondition condition) {
		if (!condition.getChildConditions().isEmpty() && !condition.getConditionAttributes().isEmpty()) {
			throw new ConditionInconsistentException("Content conditions must have either nested conditions or condition attributes, but not both.");
		}
		if (condition.getChildConditions().isEmpty() && condition.getConditionAttributes().isEmpty()) {
			throw new ConditionInconsistentException("Content conditions must have either nested conditions or condition attributes.");
		}
	}
}