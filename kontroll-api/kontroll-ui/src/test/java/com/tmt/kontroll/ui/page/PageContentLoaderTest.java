package com.tmt.kontroll.ui.page;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.ContentService;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.ui.page.layout.PageLayout;
import com.tmt.kontroll.ui.page.layout.PageLayoutBody;
import com.tmt.kontroll.ui.page.layout.PageLayoutContent;
import com.tmt.kontroll.ui.page.layout.PageLayoutFooter;
import com.tmt.kontroll.ui.page.layout.PageLayoutHeader;
import com.tmt.kontroll.ui.page.layout.PageLayoutSegment;

@RunWith(MockitoJUnitRunner.class)
public class PageContentLoaderTest {
	
	@Mock
	private ContentService contentService;
	@Mock
	private GlobalContext globalContext;
	@Mock
	private PageContentManager pageContentManager;
	@Mock
	private PageLayout pageLayout;
	@Mock
	private PageLayoutHeader pageLayoutHeader;
	@Mock
	private PageLayoutBody pageLayoutBody;
	@Mock
	private PageLayoutFooter pageLayoutFooter;
	@Mock
	private PageLayoutSegment pageLayoutSegment;
	@Mock
	private PageLayoutContent pageLayoutContent;

	private final String scopeName = "scopeName";

	private PageContentLoader toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new PageContentLoader();
		this.toTest.contentService = this.contentService;
		this.toTest.globalContext = this.globalContext;
		this.toTest.pageContentManager = this.pageContentManager;
		this.toTest.pageLayout = this.pageLayout;
		when(this.pageLayoutContent.getScopeName()).thenReturn(this.scopeName);
		when(this.pageLayoutSegment.getScopeName()).thenReturn(this.scopeName);
		when(this.pageContentManager.fetchPageLayoutContent(any(String.class), any(String.class))).thenReturn(this.pageLayoutContent);
		when(this.pageLayout.fetchBody(any(String.class))).thenReturn(this.pageLayoutBody);
		when(this.pageLayout.fetchHeader(any(String.class))).thenReturn(this.pageLayoutHeader);
		when(this.pageLayout.fetchFooter(any(String.class))).thenReturn(this.pageLayoutFooter);
	}

	@Test
	@SuppressWarnings({"unchecked", "serial"})
	public void testThatLoadContentIsLoadedForPageLayoutContentElements() throws Exception {
		//given
		when(this.pageLayoutBody.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
			add(pageLayoutContent);
		}});
		when(this.pageLayoutHeader.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
			add(pageLayoutContent);
		}});
		when(this.pageLayoutFooter.getChildren()).thenReturn(new ArrayList<PageLayoutSegment>(){{
			add(pageLayoutContent);
		}});
		
		//when
		this.toTest.loadContent(this.scopeName);
		
		//then
		verify(this.contentService, times(3)).loadContent(any(ContentDto.class));
		verify(this.pageLayoutContent, times(3)).setContent(any(List.class));
	}
	
	@Test
	@SuppressWarnings({"unchecked", "serial"})
	public void testThatLoadContentIsNotLoadedForNotPageLayoutContentElements() throws Exception {
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
		this.toTest.loadContent(this.scopeName);
		
		//then
		verify(this.contentService, never()).loadContent(any(ContentDto.class));
		verify(this.pageLayoutContent, never()).setContent(any(List.class));
	}
	
}
