package com.tmt.kontroll.ui.page.management;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.content.ContentDto;
import com.tmt.kontroll.content.ContentService;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.context.request.RequestContextItem;
import com.tmt.kontroll.ui.exceptions.PageManagementException;
import com.tmt.kontroll.ui.page.Page;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.layout.impl.PageContent;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;

public class PageManagerTest {

	@PageConfig(contexts = {@PageContext(scope = "dummy")})
	public static class DummyPageSegment extends PageContent {
		@Override
		public String getScopeName() {
			return "dummy";
		}
	}

	@PageConfig(contexts = {@PageContext(scope = "wurst", pattern = "/wurst"),
	                        @PageContext(scope = "dummy")})
	public static class WurstPageSegment extends PageSegment {
		@Override
		public String getScopeName() {
			return "wurst";
		}
	}

	@Mock
	Page																				page;
	@Mock
	TreeMap<PageSegmentOrdinalKey, PageSegment>	treeMap;
	@Mock
	PageHolder																	pageHolder;
	@Mock
	PageSegmentHolder														pageSegmentHolder;
	@Mock
	GlobalContext																globalContext;
	@Mock
	GlobalContextDto														globalContextDto;
	@Mock
	RequestContextItem													requestContextItem;
	@Mock
	ContentService															contentService;

	private PageManager													toTest;

	@Before
	@SuppressWarnings("serial")
	public void setUp() throws Exception {
		initMocks(this);
		when(this.treeMap.values()).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(new DummyPageSegment());
			}
		});
		when(this.page.getChildren()).thenReturn(this.treeMap);
		when(this.pageSegmentHolder.fetchPageSegments(any(String.class))).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(new WurstPageSegment());
			}
		});
		when(this.pageHolder.fetchPageByPath("request")).thenReturn(this.page);
		when(this.globalContext.getGlobalContextDto()).thenReturn(this.globalContextDto);
		when(this.globalContext.fetchRequestContext(any(String.class))).thenReturn(new HashSet<RequestContextItem>() {
			{
				this.add(PageManagerTest.this.requestContextItem);
			}
		});
		this.toTest = new PageManager();
		this.toTest.pageSegmentHolder = this.pageSegmentHolder;
		this.toTest.pageHolder = this.pageHolder;
		this.toTest.globalContext = this.globalContext;
		this.toTest.contentService = this.contentService;

	}

	@Test
	public void testThatManageWorksForPageLoad() throws Exception {
		//when
		this.toTest.manage("request", "page");
		//then
		verify(this.contentService).loadContent(any(ContentDto.class));
	}

	@Test
	public void testThatManageWorksForScopeLoad() throws Exception {
		//when
		this.toTest.manage("/request", "dummy");
		//then
		verify(this.contentService, never()).loadContent(any(ContentDto.class));
	}

	@Test(expected = PageManagementException.class)
	public void testThatManageThrowsException() throws Exception {
		//given
		when(this.pageSegmentHolder.fetchPageSegments(any(String.class))).thenReturn(new ArrayList<PageSegment>());
		//when
		this.toTest.manage("/request", "dummy");
		//then
		verify(this.contentService, never()).loadContent(any(ContentDto.class));
	}
}
