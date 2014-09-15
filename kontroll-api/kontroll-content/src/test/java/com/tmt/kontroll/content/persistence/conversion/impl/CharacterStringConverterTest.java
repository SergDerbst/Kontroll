package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CharacterStringConverterTest extends ValueStringConverterTest<Character, String> {
	
	public CharacterStringConverterTest() {
		super(new CharacterStringConverter(), 
		      Character.class, 
		      new Character("0".charAt(0)), 
		      "0", 
		      String.class, 
		      "boing", 
		      "boing");
	}
	
	@Test
	public void testThatResponsibilityWorksForPrimitive() {
		assertEquals(new Character("b".charAt(0)), new CharacterStringConverter().convertToValue("b", Character.TYPE));
	}
	
	@Test(expected = RuntimeException.class)
	public void testThatStringsLongerThanOneCauseAnException() {
		new CharacterStringConverter().convertToValue("boom", Character.class);
	}
}
