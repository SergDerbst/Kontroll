package com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl;

import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.ConstraintAssertion;

public class UniqueConstraintOnTableAssertion extends ConstraintAssertion {

	private final UniqueConstraint constraint;

	public UniqueConstraintOnTableAssertion(final UniqueConstraint constraint) {
		this.constraint = constraint;
	}

	@Override
	public String name() {
		return this.constraint.name();
	}

	public String[] columns() {
		return this.constraint.columnNames();
	}
}
