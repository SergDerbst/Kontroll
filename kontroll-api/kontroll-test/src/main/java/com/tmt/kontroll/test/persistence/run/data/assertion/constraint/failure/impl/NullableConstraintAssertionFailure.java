package com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.impl;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.NullableConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.ConstraintAssertionFailure;

public class NullableConstraintAssertionFailure extends ConstraintAssertionFailure<NullableConstraintAssertion> {

	public NullableConstraintAssertionFailure(final NullableConstraintAssertion constraintAssertion,
	                                          final Class<?> entityClass) {
		super(constraintAssertion, entityClass);
	}

	@Override
	public String failureMessage() {
		return "Nullable constraint on column: " + super.constraintAssertion().name();
	}
}
