package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import org.junit.Test;

public class ConditionAttributeDoubleValueVerifierTest extends ConditionAttributeValueVerifierTest<Double, String> {

	public ConditionAttributeDoubleValueVerifierTest() {
		super(new ConditionAttributeDoubleValueVerifier(),
		      Double.class,
		      new Double(0),
		      String.class,
		"blubb");
	}

	@Test
	public void testThatIsEqualWorks() {
		super.testIsEqual((double) 0, (double) 0, true);
		super.testIsEqual((double) 0, (double) 1, false);
	}

	@Test
	public void testThatIsNotEqualWorks() {
		super.testIsNotEqual((double) 0, (double) 1, true);
		super.testIsNotEqual((double) 0, (double) 0, false);
	}

	@Test
	public void testThatIsLargerAsWorks() {
		super.testIsLargerAs((double) 1, (double) 0, true);
		super.testIsLargerAs((double) 0, (double) 0, false);
		super.testIsLargerAs((double) 0, (double) 1, false);
	}

	@Test()
	public void testThatIsLargerOrEqualAsWorks() {
		super.testIsLargerOrEqualAs((double) 1, (double) 0, true);
		super.testIsLargerOrEqualAs((double) 0, (double) 0, true);
		super.testIsLargerOrEqualAs((double) 0, (double) 1, false);
	}

	@Test()
	public void testThatIsSmallerAsWorks() {
		super.testIsSmallerAs((double) 0, (double) 1, true);
		super.testIsSmallerAs((double) 0, (double) 0, false);
		super.testIsSmallerAs((double) 1, (double) 0, false);
	}

	@Test()
	public void testThatIsSmallerOrEqualAsCausesRuntimeException() {
		super.testIsSmallerOrEqualAs((double) 0, (double) 1, true);
		super.testIsSmallerOrEqualAs((double) 0, (double) 0, true);
		super.testIsSmallerOrEqualAs((double) 1, (double) 0, false);
	}
}
