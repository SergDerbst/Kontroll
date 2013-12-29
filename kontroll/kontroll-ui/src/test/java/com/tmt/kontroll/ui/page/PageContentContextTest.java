package com.tmt.kontroll.ui.page;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.test.ObjectDataTest;

@RunWith(MockitoJUnitRunner.class)
public class PageContentContextTest extends ObjectDataTest<PageContentContext> {

	private static final Pattern patternOne = Pattern.compile("abc");
	private static final Pattern patternTwo = Pattern.compile("def");
	private static final String scopeNameOne = "scopeNameOne";
	private static final String scopeNameTwo = "scopeNameTwo";
	
	public PageContentContextTest() {
		super(new PageContentContext(patternTwo, scopeNameOne));
	}

	@Test
	public void testThatCompareWorksWithDifferentPatternsButSameStrings() {
		//given
		PageContentContext contextOne = new PageContentContext(patternOne, scopeNameOne);
		PageContentContext contextTwo = new PageContentContext(patternTwo, scopeNameOne);
		
		//when
		final int comparisonOne = contextOne.compareTo(contextTwo);
		final int comparisonTwo = contextTwo.compareTo(contextOne);
		
		//then
		assertTrue(comparisonOne < 0);
		assertTrue(comparisonTwo > 0);
	}
	
	@Test
	public void testThatCompareWorksWithSamePatternsButDifferentStrings() {
		//given
		PageContentContext contextOne = new PageContentContext(patternOne, scopeNameOne);
		PageContentContext contextTwo = new PageContentContext(patternOne, scopeNameTwo);
		
		//when
		final int comparisonOne = contextOne.compareTo(contextTwo);
		final int comparisonTwo = contextTwo.compareTo(contextOne);
		
		//then
		assertTrue(comparisonOne < 0);
		assertTrue(comparisonTwo > 0);
	}
	
	@Test
	public void testThatCompareWorksWithSamePatternsAndSameStrings() {
		//given
		PageContentContext contextOne = new PageContentContext(patternOne, scopeNameOne);
		PageContentContext contextTwo = new PageContentContext(patternOne, scopeNameOne);
		
		//when
		final int comparisonOne = contextOne.compareTo(contextTwo);
		final int comparisonTwo = contextTwo.compareTo(contextOne);
		
		//then
		assertTrue(comparisonOne == 0);
		assertTrue(comparisonTwo == 0);
	}
}
