package com.tmt.kontroll.content.persistence.conversion.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShortStringConverterTest extends ValueStringConverterTest<Short, String> {

	public ShortStringConverterTest() {
		super(new ShortStringConverter(), 
		      Short.class, 
		      new Short((byte) 0), 
		      "0", 
		      String.class, 
		      "boing", 
		      "boing");
	}
	
	@Test
	public void testThatResponsibilityWorksForPrimitive() {
		assertEquals((short) 0, new ShortStringConverter().convertToValue("0", Short.TYPE));
	}
}
