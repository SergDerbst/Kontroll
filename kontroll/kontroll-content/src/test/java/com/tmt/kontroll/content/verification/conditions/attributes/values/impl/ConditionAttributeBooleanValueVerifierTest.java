package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import org.junit.Test;


public class ConditionAttributeBooleanValueVerifierTest extends ConditionAttributeValueVerifierTest<Boolean, String> {

	public ConditionAttributeBooleanValueVerifierTest() {
		super(new ConditionAttributeBooleanValueVerifier(),
		      Boolean.class,
		      false,
		      String.class,
		"blubb");
	}

	@Test
	public void testThatIsEqualWorks() {
		super.testIsEqual(false, false, true);
		super.testIsEqual(false, true, false);
	}

	@Test
	public void testThatIsNotEqualWorks() {
		super.testIsNotEqual(false, true, true);
		super.testIsNotEqual(false, false, false);
	}

	@Test(expected = RuntimeException.class)
	public void testThatIsLargerAsCausesRuntimeException() {
		super.testIsLargerAs(false, true, true);
	}

	@Test(expected = RuntimeException.class)
	public void testThatIsLargerOrEqualAsCausesRuntimeException() {
		super.testIsLargerOrEqualAs(false, true, true);
	}

	@Test(expected = RuntimeException.class)
	public void testThatIsSmallerAsCausesRuntimeException() {
		super.testIsSmallerAs(false, true, true);
	}

	@Test(expected = RuntimeException.class)
	public void testThatIsSmallerOrEqualAsCausesRuntimeException() {
		super.testIsSmallerOrEqualAs(false, true, true);
	}
}
