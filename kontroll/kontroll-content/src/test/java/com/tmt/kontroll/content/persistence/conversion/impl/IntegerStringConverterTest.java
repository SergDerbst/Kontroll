package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntegerStringConverterTest extends ValueStringConverterTest<Integer, String> {

	public IntegerStringConverterTest() {
		super(new IntegerStringConverter(), 
		      Integer.class, 
		      new Integer((byte) 0), 
		      "0", 
		      String.class, 
		      "boing", 
		      "boing");
	}
	
	@Test
	public void testThatResponsibilityWorksForPrimitive() {
		assertEquals(0, new IntegerStringConverter().convertToValue("0", Integer.TYPE));
	}
}
