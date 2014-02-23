package com.tmt.kontroll.test.persistence.run.data.assertion.constraint;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.ConstraintAssertionFailure;

/**
 * The constraint asserter handles assertions of whether a constraint violation had caused
 * an appropriate error or not. Essentially, it is just a container of test failures in that
 * regard, that if a test had tried to provoke a constraint violation error and that error
 * did not occur, it will add the respective {@link ConstraintAssertionFailure} to the constraint.
 * Therefore, the assertertion now just checks whether there are any failures and if so, fail the
 * test with a failure message containing the constraint violation test failures.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class ConstraintAsserter {

	private static class InstanceHolder {
		public static ConstraintAsserter instance;
	}

	public static ConstraintAsserter newInstance() {
		InstanceHolder.instance = new ConstraintAsserter();
		return InstanceHolder.instance;
	}

	private final List<ConstraintAssertionFailure<?>> failures = new ArrayList<>();

	private ConstraintAsserter() {}

	public void addFailure(final ConstraintAssertionFailure<?> failure) {
		this.failures.add(failure);
	}

	public void assertConstraints() {
		if (!this.failures.isEmpty()) {
			fail(this.failureMessage());
		}
	}

	private String failureMessage() {
		final StringBuilder sB = new StringBuilder("Constraint assertion failed. Failures:");
		for (final ConstraintAssertionFailure<?> failure : this.failures) {
			sB.append("\n");
			sB.append(failure.failureMessage());
		}
		return sB.toString();
	}
}
