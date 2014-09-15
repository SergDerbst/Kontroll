package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class BooleanStringConverterTest extends ValueStringConverterTest<Boolean, String> {

	public BooleanStringConverterTest() {
		super(new BooleanStringConverter(), 
		      Boolean.class, 
		      true, 
		      "true", 
		      String.class, 
		      "boing", 
		      "boing");
	}
	
	@Test
	public void testThatResponsibilityWorksForPrimitive() {
		assertEquals(true, new BooleanStringConverter().convertToValue("true", Boolean.TYPE));
	}
}
