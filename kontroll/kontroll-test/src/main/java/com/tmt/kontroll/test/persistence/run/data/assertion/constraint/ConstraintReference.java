package com.tmt.kontroll.test.persistence.run.data.assertion.constraint;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.ConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;

public class ConstraintReference extends EntityReference {

	private final ConstraintAssertion constraintAssertion;

	public ConstraintReference(final Object entity,
	                           final ConstraintAssertion constraintAssertion) {
		super(entity);
		this.constraintAssertion = constraintAssertion;
	}

	public ConstraintAssertion getConstraintAssertion() {
		return this.constraintAssertion;
	}
}
