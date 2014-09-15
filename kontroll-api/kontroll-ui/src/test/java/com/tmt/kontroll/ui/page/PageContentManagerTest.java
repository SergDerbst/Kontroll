package com.tmt.kontroll.ui.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.commons.utils.RegexPatternComparator;
import com.tmt.kontroll.ui.page.layout.PageLayout;
import com.tmt.kontroll.ui.page.layout.PageLayoutBody;
import com.tmt.kontroll.ui.page.layout.PageLayoutContent;
import com.tmt.kontroll.ui.page.layout.PageLayoutFooter;
import com.tmt.kontroll.ui.page.layout.PageLayoutHeader;
import com.tmt.kontroll.ui.page.layout.PageLayoutSegment;

@RunWith(MockitoJUnitRunner.class)
public class PageContentManagerTest {

	private static final String regex = "regex";
	private static final String scopeName = "scopeName";
	private static final Pattern pattern = Pattern.compile(regex);
	
	@Mock
	private PageLayout pageLayout;
	@Mock
	private PageLayoutHeader pageLayoutHeader;
	@Mock
	private PageLayoutBody pageLayoutBody;
	@Mock
	private PageLayoutFooter pageLayoutFooter;
	@Mock
	private PageLayoutContent pageLayoutContent;
	@Mock
	private PageLayoutSegment pageLayoutSegment;
	@Mock
	private TreeMap<PageContentContext, PageLayoutContent> contentMap;

	private PageContentContext pageContentContext;
	private PageContentManager toTest;

	@Before
	@SuppressWarnings("serial")
	public void setUp() throws Exception {
		this.toTest = new PageContentManager();
		this.toTest.pageLayout = this.pageLayout;
		this.toTest.contentMap = this.contentMap;
		when(this.pageLayoutBody.getScopeName()).thenReturn(scopeName);
		when(this.pageLayoutHeader.getScopeName()).thenReturn(scopeName);
		when(this.pageLayoutFooter.getScopeName()).thenReturn(scopeName);
		when(this.pageLayoutContent.getScopeName()).thenReturn(scopeName);
		when(this.pageLayoutSegment.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
			add(pageLayoutContent);
		}});
		when(this.pageLayout.getBodyMap()).thenReturn(new TreeMap<Pattern, PageLayoutBody>(new RegexPatternComparator()) {{
			put(pattern, pageLayoutBody);
		}});
		when(this.pageLayout.getHeaderMap()).thenReturn(new TreeMap<Pattern, PageLayoutHeader>(new RegexPatternComparator()) {{
			put(pattern, pageLayoutHeader);
		}});
		when(this.pageLayout.getFooterMap()).thenReturn(new TreeMap<Pattern, PageLayoutFooter>(new RegexPatternComparator()) {{
			put(pattern, pageLayoutFooter);
		}});
		this.pageContentContext = new PageContentContext(pattern, scopeName);
		when(this.contentMap.descendingMap()).thenReturn(new TreeMap<PageContentContext, PageLayoutContent>() {{
			put(pageContentContext, pageLayoutContent);
		}});
	}

	@Test
	@SuppressWarnings("serial")
	public void testThatContentIsProperlyPreparedForContentSegments() throws Exception {
		//given
		when(this.pageLayoutBody.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>() {{
			add(pageLayoutContent);
		}});
		when(this.pageLayoutHeader.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
			add(pageLayoutContent);
		}});
		when(this.pageLayoutFooter.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
			add(pageLayoutContent);
		}});
		
		//when
		this.toTest.prepareContent();
		
		//then
		verify(this.contentMap, times(3)).put(any(PageContentContext.class), any(PageLayoutContent.class));
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testThatContentIsProperlyPreparedForNonContentSegments() throws Exception {		
	//given
			when(this.pageLayoutBody.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
				add(pageLayoutSegment);
			}});
			when(this.pageLayoutHeader.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
				add(pageLayoutSegment);
			}});
			when(this.pageLayoutFooter.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
				add(pageLayoutSegment);
			}});
			
		//when
		this.toTest.prepareContent();
		
		//then
		verify(this.contentMap, times(3)).put(any(PageContentContext.class), any(PageLayoutContent.class));
	}

	@Test
	public void testThatFetchPageLayoutContentForExistingContent() throws Exception {
		//when
		final PageLayoutContent content = this.toTest.fetchPageLayoutContent(regex, scopeName);
		
		//then
		assertEquals(this.pageLayoutContent, content);
	}
	
	@Test
	public void testThatFetchPageLayoutContentReturnNullForNonMatchingPattern() throws Exception {
		//when
		final PageLayoutContent content = this.toTest.fetchPageLayoutContent("wurst", scopeName);
		
		//then
		assertNull(content);
	}
	
	@Test
	public void testThatFetchPageLayoutContentReturnNullForNonMatchingScopeName() throws Exception {
		//when
		final PageLayoutContent content = this.toTest.fetchPageLayoutContent(regex, "wurst");
		
		//then
		assertNull(content);
	}
}
