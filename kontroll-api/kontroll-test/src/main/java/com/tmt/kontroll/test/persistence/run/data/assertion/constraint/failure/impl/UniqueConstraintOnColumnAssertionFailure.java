package com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.impl;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.UniqueConstraintOnColumnAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.ConstraintAssertionFailure;

public class UniqueConstraintOnColumnAssertionFailure extends ConstraintAssertionFailure<UniqueConstraintOnColumnAssertion> {

	public UniqueConstraintOnColumnAssertionFailure(final UniqueConstraintOnColumnAssertion constraintAssertion,
	                                                final Class<?> entityClass) {
		super(constraintAssertion, entityClass);
	}

	@Override
	public String failureMessage() {
		return "Unique constraint on column: " + super.constraintAssertion().name();
	}
}
