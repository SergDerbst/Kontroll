package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import org.junit.Test;

public class ConditionAttributeCharacterValueVerifierTest extends ConditionAttributeValueVerifierTest<Character, String> {

	public ConditionAttributeCharacterValueVerifierTest() {
		super(new ConditionAttributeCharacterValueVerifier(),
		      Character.class,
		      new Character("0".charAt(0)),
		      String.class,
		"blubb");
	}

	@Test
	public void testThatIsEqualWorks() {
		super.testIsEqual("0".charAt(0), "0".charAt(0), true);
		super.testIsEqual("1".charAt(0), "0".charAt(0), false);
	}

	@Test
	public void testThatIsNotEqualWorks() {
		super.testIsNotEqual("1".charAt(0), "0".charAt(0), true);
		super.testIsNotEqual("1".charAt(0), "1".charAt(0), false);
	}

	@Test
	public void testThatIsLargerAsWorks() {
		super.testIsLargerAs("1".charAt(0), "0".charAt(0), true);
		super.testIsLargerAs("0".charAt(0), "1".charAt(0), false);
		super.testIsLargerAs("1".charAt(0), "1".charAt(0), false);
	}

	@Test()
	public void testThatIsLargerOrEqualAsWorks() {
		super.testIsLargerOrEqualAs("1".charAt(0), "0".charAt(0), true);
		super.testIsLargerOrEqualAs("1".charAt(0), "1".charAt(0), true);
		super.testIsLargerOrEqualAs("0".charAt(0), "1".charAt(0), false);
	}

	@Test()
	public void testThatIsSmallerAsWorks() {
		super.testIsSmallerAs("0".charAt(0), "1".charAt(0), true);
		super.testIsSmallerAs("1".charAt(0), "0".charAt(0), false);
		super.testIsSmallerAs("1".charAt(0), "1".charAt(0), false);
	}

	@Test()
	public void testThatIsSmallerOrEqualAsCausesRuntimeException() {
		super.testIsSmallerOrEqualAs("0".charAt(0), "1".charAt(0), true);
		super.testIsSmallerOrEqualAs("1".charAt(0), "1".charAt(0), true);
		super.testIsSmallerOrEqualAs("1".charAt(0), "0".charAt(0), false);
	}
}
