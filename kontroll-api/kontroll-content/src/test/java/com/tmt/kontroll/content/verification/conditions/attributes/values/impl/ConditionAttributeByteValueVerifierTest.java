package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import org.junit.Test;

public class ConditionAttributeByteValueVerifierTest extends ConditionAttributeValueVerifierTest<Byte, String> {

	public ConditionAttributeByteValueVerifierTest() {
		super(new ConditionAttributeByteValueVerifier(),
		      Byte.class,
		      new Byte((byte) 0),
		      String.class,
		"blubb");
	}

	@Test
	public void testThatIsEqualWorks() {
		super.testIsEqual((byte) 0, (byte) 0, true);
		super.testIsEqual((byte) 0, (byte) 1, false);
	}

	@Test
	public void testThatIsNotEqualWorks() {
		super.testIsNotEqual((byte) 0, (byte) 1, true);
		super.testIsNotEqual((byte) 0, (byte) 0, false);
	}

	@Test
	public void testThatIsLargerAsWorks() {
		super.testIsLargerAs((byte) 1, (byte) 0, true);
		super.testIsLargerAs((byte) 0, (byte) 0, false);
		super.testIsLargerAs((byte) 0, (byte) 1, false);
	}

	@Test()
	public void testThatIsLargerOrEqualAsWorks() {
		super.testIsLargerOrEqualAs((byte) 1, (byte) 0, true);
		super.testIsLargerOrEqualAs((byte) 0, (byte) 0, true);
		super.testIsLargerOrEqualAs((byte) 0, (byte) 1, false);
	}

	@Test()
	public void testThatIsSmallerAsWorks() {
		super.testIsSmallerAs((byte) 0, (byte) 1, true);
		super.testIsSmallerAs((byte) 0, (byte) 0, false);
		super.testIsSmallerAs((byte) 1, (byte) 0, false);
	}

	@Test()
	public void testThatIsSmallerOrEqualAsCausesRuntimeException() {
		super.testIsSmallerOrEqualAs((byte) 0, (byte) 1, true);
		super.testIsSmallerOrEqualAs((byte) 0, (byte) 0, true);
		super.testIsSmallerOrEqualAs((byte) 1, (byte) 0, false);
	}
}
