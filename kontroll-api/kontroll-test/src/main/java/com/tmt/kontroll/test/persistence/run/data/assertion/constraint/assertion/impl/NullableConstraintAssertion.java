package com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl;

import javax.persistence.Column;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.ConstraintAssertion;

public class NullableConstraintAssertion extends ConstraintAssertion {

	private final Column column;

	public NullableConstraintAssertion(final Column column) {
		this.column = column;
	}

	@Override
	public String name() {
		return this.column.name();
	}
}
