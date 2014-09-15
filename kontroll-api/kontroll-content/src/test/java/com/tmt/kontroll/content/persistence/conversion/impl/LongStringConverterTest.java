package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongStringConverterTest extends ValueStringConverterTest<Long, String> {

	public LongStringConverterTest() {
		super(new LongStringConverter(), 
		      Long.class, 
		      new Long((byte) 0), 
		      "0", 
		      String.class, 
		      "boing", 
		      "boing");
	}
	
	@Test
	public void testThatResponsibilityWorksForPrimitive() {
		assertEquals(0l, new LongStringConverter().convertToValue("0", Long.TYPE));
	}
}
