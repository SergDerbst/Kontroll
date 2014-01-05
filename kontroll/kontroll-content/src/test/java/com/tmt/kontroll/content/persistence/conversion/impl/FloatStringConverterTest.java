package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FloatStringConverterTest extends ValueStringConverterTest<Float, String> {

	public FloatStringConverterTest() {
		super(new FloatStringConverter(), 
		      Float.class, 
		      new Float((byte) 0), 
		      "0.0", 
		      String.class, 
		      "boing", 
		      "boing");
	}
	
	@Test
	public void testThatResponsibilityWorksForPrimitive() {
		assertEquals((float) 0, new FloatStringConverter().convertToValue("0.0", Float.TYPE));
	}
}
