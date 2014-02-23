package com.tmt.kontroll.test.persistence.run.data.assertion.constraint;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.failure.ConstraintAssertionFailure;

public class ConstraintAsserterTest {

	@Mock
	private ConstraintAssertionFailure<?> constraintAssertionFailure;

	private ConstraintAsserter toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.toTest = ConstraintAsserter.newInstance();
	}

	@Test
	public void testThatAnExistingConstraintAssertionFailuresCauseFailTheTest() {
		//given
		this.toTest.addFailure(this.constraintAssertionFailure);
		//when
		try {
			this.toTest.assertConstraints();
		} catch (final AssertionError e) {
			//awesome
		}
		//then
		verify(this.constraintAssertionFailure).failureMessage();
	}

	@Test
	public void testThatZeroConstraintAssertionFailuresDoNotFailTheTest() {
		//when
		this.toTest.assertConstraints();
		//then all should be fine now
	}
}
