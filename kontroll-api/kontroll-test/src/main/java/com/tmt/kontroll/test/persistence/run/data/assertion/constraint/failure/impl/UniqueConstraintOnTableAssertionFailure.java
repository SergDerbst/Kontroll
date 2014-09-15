package com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.impl;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.UniqueConstraintOnTableAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.ConstraintAssertionFailure;

public class UniqueConstraintOnTableAssertionFailure extends ConstraintAssertionFailure<UniqueConstraintOnTableAssertion> {

	public UniqueConstraintOnTableAssertionFailure(final Class<?> entityClass,
	                                               final UniqueConstraintOnTableAssertion constraintAssertion) {
		super(constraintAssertion, entityClass);
	}

	@Override
	public String failureMessage() {
		final StringBuilder sB = new StringBuilder();
		sB.append("Unique constraint on table, named: " + super.constraintAssertion().name());
		sB.append(", with columns: ");
		for (final String columnName : super.constraintAssertion().columns()) {
			sB.append("\n");
			sB.append("\t" + columnName);
		}
		return sB.toString();
	}
}
