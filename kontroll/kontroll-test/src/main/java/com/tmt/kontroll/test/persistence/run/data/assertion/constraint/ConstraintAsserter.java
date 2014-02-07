package com.tmt.kontroll.test.persistence.run.data.assertion.constraint;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.ConstraintAssertionFailure;

public class ConstraintAsserter {

	private static class InstanceHolder {
		public static ConstraintAsserter instance;
	}

	public static ConstraintAsserter newInstance() {
		InstanceHolder.instance = new ConstraintAsserter();
		return InstanceHolder.instance;
	}

	private final List<ConstraintAssertionFailure> failures = new ArrayList<>();

	private ConstraintAsserter() {}

	public void addFailure(final ConstraintAssertionFailure failure) {
		this.failures.add(failure);
	}

	public void assertConstraints() {
		if (!this.failures.isEmpty()) {
			fail(this.failureMessage());
		}
	}

	private String failureMessage() {
		final StringBuilder sB = new StringBuilder("Constraint assertion failed. Failures:");
		for (final ConstraintAssertionFailure failure : this.failures) {
			sB.append("\n");
			sB.append(failure.failureMessage());
		}
		return sB.toString();
	}
}
