package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import org.junit.Test;

public class ConditionAttributeLongValueVerifierTest extends ConditionAttributeValueVerifierTest<Long, String> {

	public ConditionAttributeLongValueVerifierTest() {
		super(new ConditionAttributeLongValueVerifier(),
		      Long.class,
		      new Long(0),
		      String.class,
		"blubb");
	}

	@Test
	public void testThatIsEqualWorks() {
		super.testIsEqual((long) 0, (long) 0, true);
		super.testIsEqual((long) 0, (long) 1, false);
	}

	@Test
	public void testThatIsNotEqualWorks() {
		super.testIsNotEqual((long) 0, (long) 1, true);
		super.testIsNotEqual((long) 0, (long) 0, false);
	}

	@Test
	public void testThatIsLargerAsWorks() {
		super.testIsLargerAs((long) 1, (long) 0, true);
		super.testIsLargerAs((long) 0, (long) 0, false);
		super.testIsLargerAs((long) 0, (long) 1, false);
	}

	@Test()
	public void testThatIsLargerOrEqualAsWorks() {
		super.testIsLargerOrEqualAs((long) 1, (long) 0, true);
		super.testIsLargerOrEqualAs((long) 0, (long) 0, true);
		super.testIsLargerOrEqualAs((long) 0, (long) 1, false);
	}

	@Test()
	public void testThatIsSmallerAsWorks() {
		super.testIsSmallerAs((long) 0, (long) 1, true);
		super.testIsSmallerAs((long) 0, (long) 0, false);
		super.testIsSmallerAs((long) 1, (long) 0, false);
	}

	@Test()
	public void testThatIsSmallerOrEqualAsCausesRuntimeException() {
		super.testIsSmallerOrEqualAs((long) 0, (long) 1, true);
		super.testIsSmallerOrEqualAs((long) 0, (long) 0, true);
		super.testIsSmallerOrEqualAs((long) 1, (long) 0, false);
	}
}
