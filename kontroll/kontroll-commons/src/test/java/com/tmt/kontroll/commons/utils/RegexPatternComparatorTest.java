package com.tmt.kontroll.commons.utils;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class RegexPatternComparatorTest {

	private static final Pattern smallPattern = Pattern.compile("abc");
	private static final Pattern bigPattern = Pattern.compile("def");
	
	private RegexPatternComparator toTest;
	
	@Before
	public void setUp() throws Exception {
		this.toTest = new RegexPatternComparator();
	}

	@Test
	public void testThatAscendingPatternsAreComparedCorrectly() {
		//when
		final int ascending = this.toTest.compare(smallPattern, bigPattern);
		
		//then
		assertTrue(ascending < 0);
	}
	
	@Test
	public void testThatDescendingPatternsAreComparedCorrectly() {
		//when
		final int ascending = this.toTest.compare(bigPattern, smallPattern);
		
		//then
		assertTrue(ascending > 0);
	}
	
	@Test
	public void testThatEqualPatternsAreComparedCorrectly() {
		//when
		final int ascending = this.toTest.compare(smallPattern, smallPattern);
		
		//then
		assertTrue(ascending == 0);
	}
}
