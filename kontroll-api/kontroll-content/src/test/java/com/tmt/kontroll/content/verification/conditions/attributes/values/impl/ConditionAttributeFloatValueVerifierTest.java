package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import org.junit.Test;

public class ConditionAttributeFloatValueVerifierTest extends ConditionAttributeValueVerifierTest<Float, String> {

	public ConditionAttributeFloatValueVerifierTest() {
		super(new ConditionAttributeFloatValueVerifier(),
		      Float.class,
		      new Float(0),
		      String.class,
		"blubb");
	}

	@Test
	public void testThatIsEqualWorks() {
		super.testIsEqual((float) 0, (float) 0, true);
		super.testIsEqual((float) 0, (float) 1, false);
	}

	@Test
	public void testThatIsNotEqualWorks() {
		super.testIsNotEqual((float) 0, (float) 1, true);
		super.testIsNotEqual((float) 0, (float) 0, false);
	}

	@Test
	public void testThatIsLargerAsWorks() {
		super.testIsLargerAs((float) 1, (float) 0, true);
		super.testIsLargerAs((float) 0, (float) 0, false);
		super.testIsLargerAs((float) 0, (float) 1, false);
	}

	@Test()
	public void testThatIsLargerOrEqualAsWorks() {
		super.testIsLargerOrEqualAs((float) 1, (float) 0, true);
		super.testIsLargerOrEqualAs((float) 0, (float) 0, true);
		super.testIsLargerOrEqualAs((float) 0, (float) 1, false);
	}

	@Test()
	public void testThatIsSmallerAsWorks() {
		super.testIsSmallerAs((float) 0, (float) 1, true);
		super.testIsSmallerAs((float) 0, (float) 0, false);
		super.testIsSmallerAs((float) 1, (float) 0, false);
	}

	@Test()
	public void testThatIsSmallerOrEqualAsCausesRuntimeException() {
		super.testIsSmallerOrEqualAs((float) 0, (float) 1, true);
		super.testIsSmallerOrEqualAs((float) 0, (float) 0, true);
		super.testIsSmallerOrEqualAs((float) 1, (float) 0, false);
	}
}
