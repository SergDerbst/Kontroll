package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import org.junit.Test;

public class ConditionAttributeShortValueVerifierTest extends ConditionAttributeValueVerifierTest<Short, String> {

	public ConditionAttributeShortValueVerifierTest() {
		super(new ConditionAttributeShortValueVerifier(),
		      Short.class,
		      new Short((short) 0),
		      String.class,
		"blubb");
	}

	@Test
	public void testThatIsEqualWorks() {
		super.testIsEqual((short) 0, (short) 0, true);
		super.testIsEqual((short) 0, (short) 1, false);
	}

	@Test
	public void testThatIsNotEqualWorks() {
		super.testIsNotEqual((short) 0, (short) 1, true);
		super.testIsNotEqual((short) 0, (short) 0, false);
	}

	@Test
	public void testThatIsLargerAsWorks() {
		super.testIsLargerAs((short) 1, (short) 0, true);
		super.testIsLargerAs((short) 0, (short) 0, false);
		super.testIsLargerAs((short) 0, (short) 1, false);
	}

	@Test()
	public void testThatIsLargerOrEqualAsWorks() {
		super.testIsLargerOrEqualAs((short) 1, (short) 0, true);
		super.testIsLargerOrEqualAs((short) 0, (short) 0, true);
		super.testIsLargerOrEqualAs((short) 0, (short) 1, false);
	}

	@Test()
	public void testThatIsSmallerAsWorks() {
		super.testIsSmallerAs((short) 0, (short) 1, true);
		super.testIsSmallerAs((short) 0, (short) 0, false);
		super.testIsSmallerAs((short) 1, (short) 0, false);
	}

	@Test()
	public void testThatIsSmallerOrEqualAsCausesRuntimeException() {
		super.testIsSmallerOrEqualAs((short) 0, (short) 1, true);
		super.testIsSmallerOrEqualAs((short) 0, (short) 0, true);
		super.testIsSmallerOrEqualAs((short) 1, (short) 0, false);
	}
}
