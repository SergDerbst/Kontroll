package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ByteStringConverterTest extends ValueStringConverterTest<Byte, String> {

	public ByteStringConverterTest() {
		super(new ByteStringConverter(), 
		      Byte.class, 
		      new Byte((byte) 0), 
		      "0", 
		      String.class, 
		      "boing", 
		      "boing");
	}
	
	@Test
	public void testThatResponsibilityWorksForPrimitive() {
		assertEquals((byte) 0, new ByteStringConverter().convertToValue("0", Byte.TYPE));
	}
}
