package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DoubleStringConverterTest extends ValueStringConverterTest<Double, String> {

	public DoubleStringConverterTest() {
		super(new DoubleStringConverter(), 
		      Double.class, 
		      new Double((byte) 0), 
		      "0.0", 
		      String.class, 
		      "boing", 
		      "boing");
	}
	
	@Test
	public void testThatResponsibilityWorksForPrimitive() {
		assertEquals((double) 0, new DoubleStringConverter().convertToValue("0.0", Double.TYPE));
	}
}
