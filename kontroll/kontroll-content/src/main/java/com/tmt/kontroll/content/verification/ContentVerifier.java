package com.tmt.kontroll.content.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.ContentContext;
import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.verification.conditions.ConditionInconsistentException;
import com.tmt.kontroll.content.verification.conditions.ConditionVerificationChain;


/**
 * The actual component matching given application values against conditions in
 * order to deliver content.
 * 
 * @author Serg Derbst
 * 
 */
@Component
public class ContentVerifier {

	@Autowired
	ConditionVerificationChain verificationChain;
	
	public <V, C extends ContentContext> boolean verify(C context) {
		for (ScopedContentCondition condition : context.getConditions()) {
			this.assertConsistent(condition);
			this.verificationChain.verify(condition, context.getContentDTO());
		}
		return false;
	}

	private void assertConsistent(ScopedContentCondition condition) {
		if (!condition.getChildConditions().isEmpty() && !condition.getScopedContentConditionAttributes().isEmpty()) {
			throw new ConditionInconsistentException("Scoped content conditions must either have nested conditions or condition attributes, but not both.");
		}
	}
}
