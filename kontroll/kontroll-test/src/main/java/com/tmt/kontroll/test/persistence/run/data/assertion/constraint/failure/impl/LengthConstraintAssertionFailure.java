package com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.impl;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.LengthConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.ConstraintAssertionFailure;

public class LengthConstraintAssertionFailure extends ConstraintAssertionFailure<LengthConstraintAssertion> {

	public LengthConstraintAssertionFailure(final LengthConstraintAssertion constraintAssertion,
	                                        final Class<?> entityClass) {
		super(constraintAssertion, entityClass);
	}

	@Override
	public String failureMessage() {
		return "Length constraint on column: " + super.constraintAssertion().name() + ", length: " + super.constraintAssertion().value();
	}
}
