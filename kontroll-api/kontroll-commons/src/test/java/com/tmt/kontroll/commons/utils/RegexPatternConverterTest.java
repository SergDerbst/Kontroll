package com.tmt.kontroll.commons.utils;


import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class RegexPatternConverterTest {

	private static final String regex = "moronicRegex";
	private static final Pattern pattern = Pattern.compile(regex);
	
	private RegexPatternConverter toTest;
	
	@Before
	public void setUp() throws Exception {
		this.toTest = new RegexPatternConverter();
	}

	@Test
	public void testThatPatternIsConvertedCorrectlyFromRegexString() {
		//when
		final Pattern converted = this.toTest.convertToPattern(regex);
		
		//then
		assertEquals(pattern.pattern(), converted.pattern());
	}
	
	@Test
	public void testThatRegexStringIsConvertedCorrectlyFromPattern() {
		//when
		final String converted = this.toTest.convertToString(pattern);
		
		//then
		assertEquals(regex, converted);
	}
}
