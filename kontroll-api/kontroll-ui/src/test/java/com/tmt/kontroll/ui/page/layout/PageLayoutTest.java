package com.tmt.kontroll.ui.page.layout;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.commons.utils.RegexPatternConverter;
import com.tmt.kontroll.test.ObjectDataTest;

public class PageLayoutTest extends ObjectDataTest<PageLayout> {

	private static final String regex = "regex";
	
	@Mock
	private PageLayoutHeader pageLayoutHeader;
	@Mock
	private PageLayoutBody pageLayoutBody;
	@Mock
	private PageLayoutFooter pageLayoutFooter;
	
	private RegexPatternConverter regexPatternConverter;
	private PageLayout toTest;
	
	public PageLayoutTest() {
		super(new PageLayout());
	}

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.regexPatternConverter = new RegexPatternConverter();
		this.toTest = new PageLayout();
		this.toTest.regexPatternConverter = this.regexPatternConverter;
	}

	@Test
	public void testThatAddingAndGettingHeadersWorks() {
		//when
		this.toTest.addHeader(regex, this.pageLayoutHeader);

		//then
		assertEquals(this.pageLayoutHeader, this.toTest.fetchHeader(regex));
		assertEquals(this.pageLayoutHeader, this.toTest.getHeaderMap().get(Pattern.compile(regex)));
	}
	
	@Test
	public void testThatAddingAndGettingFootersWorks() {
		//when
		this.toTest.addFooter(regex, this.pageLayoutFooter);
		
		//then
		assertEquals(this.pageLayoutFooter, this.toTest.fetchFooter(regex));
		assertEquals(this.pageLayoutFooter, this.toTest.getFooterMap().get(Pattern.compile(regex)));
	}
	
	@Test
	public void testThatAddingAndGettingBodiesWorks() {
		//when
		this.toTest.addBody(regex, this.pageLayoutBody);
		
		//then
		assertEquals(this.pageLayoutBody, this.toTest.fetchBody(regex));
		assertEquals(this.pageLayoutBody, this.toTest.getBodyMap().get(Pattern.compile(regex)));
	}
}
