package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import org.junit.Test;

public class ConditionAttributeIntegerValueVerifierTest extends ConditionAttributeValueVerifierTest<Integer, String> {

	public ConditionAttributeIntegerValueVerifierTest() {
		super(new ConditionAttributeIntegerValueVerifier(),
		      Integer.class,
		      new Integer(0),
		      String.class,
		"blubb");
	}

	@Test
	public void testThatIsEqualWorks() {
		super.testIsEqual(0, 0, true);
		super.testIsEqual(0, 1, false);
	}

	@Test
	public void testThatIsNotEqualWorks() {
		super.testIsNotEqual(0, 1, true);
		super.testIsNotEqual(0, 0, false);
	}

	@Test
	public void testThatIsLargerAsWorks() {
		super.testIsLargerAs(1, 0, true);
		super.testIsLargerAs(0, 0, false);
		super.testIsLargerAs(0, 1, false);
	}

	@Test()
	public void testThatIsLargerOrEqualAsWorks() {
		super.testIsLargerOrEqualAs(1, 0, true);
		super.testIsLargerOrEqualAs(0, 0, true);
		super.testIsLargerOrEqualAs(0, 1, false);
	}

	@Test()
	public void testThatIsSmallerAsWorks() {
		super.testIsSmallerAs(0, 1, true);
		super.testIsSmallerAs(0, 0, false);
		super.testIsSmallerAs(1, 0, false);
	}

	@Test()
	public void testThatIsSmallerOrEqualAsCausesRuntimeException() {
		super.testIsSmallerOrEqualAs(0, 1, true);
		super.testIsSmallerOrEqualAs(0, 0, true);
		super.testIsSmallerOrEqualAs(1, 0, false);
	}
}
