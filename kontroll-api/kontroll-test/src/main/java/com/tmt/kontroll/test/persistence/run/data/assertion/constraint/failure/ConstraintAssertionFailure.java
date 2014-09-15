package com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.ConstraintAssertion;

public abstract class ConstraintAssertionFailure<C extends ConstraintAssertion> {

	private final C constraintAssertion;
	private final Class<?> entityClass;

	public ConstraintAssertionFailure(final C constraintAssertion,
	                                  final Class<?> entityClass) {
		this.constraintAssertion = constraintAssertion;
		this.entityClass = entityClass;
	}

	public abstract String failureMessage();

	public C constraintAssertion() {
		return this.constraintAssertion;
	}

	public Class<?> entityClass() {
		return this.entityClass;
	}
}
