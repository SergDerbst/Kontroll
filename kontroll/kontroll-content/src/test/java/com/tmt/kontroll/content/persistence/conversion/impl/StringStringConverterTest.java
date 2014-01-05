package com.tmt.kontroll.content.persistence.conversion.impl;


public class StringStringConverterTest extends ValueStringConverterTest<String, Integer> {

	public StringStringConverterTest() {
		super(new StringStringConverter(), 
		      String.class, 
		      "blubb", 
		      "blubb", 
		      Integer.class, 
		      0, 
		      "0");
	}
}
