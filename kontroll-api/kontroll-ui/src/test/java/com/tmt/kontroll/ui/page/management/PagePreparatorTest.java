package com.tmt.kontroll.ui.page.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.ui.page.Page;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentScopeContext;

@SuppressWarnings("serial")
public class PagePreparatorTest {

	@PageConfig(contexts = {@PageContext(scope = "header")})
	public static class DummyHeaderPageSegment extends PageSegment {
		@Override
		public String getScopeName() {
			return "header";
		}
	}

	@PageConfig(contexts = {@PageContext(scope = "body")})
	public static class DummyBodyPageSegment extends PageSegment {
		@Override
		public String getScopeName() {
			return "body";
		}
	}

	@PageConfig(contexts = {@PageContext(scope = "footer")})
	public static class DummyFooterPageSegment extends PageSegment {
		@Override
		public String getScopeName() {
			return "footer";
		}
	}

	@PageConfig(contexts = {@PageContext(scope = "header.dummy")})
	public static class DummyPageSegment extends PageSegment {
		@Override
		public String getScopeName() {
			return "dummy";
		}
	}

	@PageConfig(contexts = {@PageContext(scope = "header.dummy.dummier")})
	public static class DummierPageSegment extends PageSegment {
		@Override
		public String getScopeName() {
			return "dummier";
		}
	}

	@Mock
	PageHolder											pageHolder;
	@Mock
	PageSegmentHolder								pageSegmentHolder;
	@Mock
	PageSegmentScopeContext					pageSegmentScopeContext;

	private Page										page;
	private DummyHeaderPageSegment	headerSegment;
	private DummyBodyPageSegment		bodySegment;
	private DummyFooterPageSegment	footerSegment;
	private DummyPageSegment				dummySegment;
	private DummierPageSegment			dummierSegment;
	private PagePreparator					toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.page = new Page();
		this.dummySegment = new DummyPageSegment();
		this.dummierSegment = new DummierPageSegment();
		this.headerSegment = new DummyHeaderPageSegment();
		this.bodySegment = new DummyBodyPageSegment();
		this.footerSegment = new DummyFooterPageSegment();
		this.toTest = new PagePreparator();
		when(this.pageSegmentHolder.fetchAllContexts()).thenReturn(new ArrayList<PageSegmentScopeContext>() {
			{
				this.add(PagePreparatorTest.this.pageSegmentScopeContext);
			}
		});
		when(this.pageHolder.fetchPageByPattern(any())).thenReturn(this.page);
		this.toTest.pageHolder = this.pageHolder;
		this.toTest.pageSegmentHolder = this.pageSegmentHolder;
	}

	@Test
	public void testThatPrepareWorksForHeaderScope() throws Exception {
		//given
		when(this.pageSegmentScopeContext.getSegments()).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(PagePreparatorTest.this.headerSegment);
			}
		});
		when(this.pageSegmentScopeContext.getScopeName()).thenReturn("header");
		//when
		this.toTest.prepare();
		//then
		assertFalse(this.page.getChildren().isEmpty());
		assertTrue(this.page.getChildren().size() == 1);
		assertEquals(this.page.getChildren().values().toArray()[0], this.headerSegment);
	}

	@Test
	public void testThatPrepareWorksForBodyScope() throws Exception {
		//given
		when(this.pageSegmentScopeContext.getSegments()).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(PagePreparatorTest.this.bodySegment);
			}
		});
		when(this.pageSegmentScopeContext.getScopeName()).thenReturn("body");
		//when
		this.toTest.prepare();
		//then
		assertFalse(this.page.getChildren().isEmpty());
		assertTrue(this.page.getChildren().size() == 1);
		assertEquals(this.page.getChildren().values().toArray()[0], this.bodySegment);
	}

	@Test
	public void testThatPrepareWorksForFooterScope() throws Exception {
		//given
		when(this.pageSegmentScopeContext.getSegments()).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(PagePreparatorTest.this.footerSegment);
			}
		});
		when(this.pageSegmentScopeContext.getScopeName()).thenReturn("footer");
		//when
		this.toTest.prepare();
		//then
		assertFalse(this.page.getChildren().isEmpty());
		assertTrue(this.page.getChildren().size() == 1);
		assertEquals(this.page.getChildren().values().toArray()[0], this.footerSegment);
	}

	@Test
	public void testThatPrepareWorksForNotRootScopeShortPath() throws Exception {
		//given
		when(this.pageSegmentScopeContext.getSegments()).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(PagePreparatorTest.this.dummySegment);
			}
		});
		when(this.pageSegmentScopeContext.getScopeName()).thenReturn("header.dummy");
		when(this.pageSegmentHolder.fetchPageSegments("header")).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(PagePreparatorTest.this.headerSegment);
			}
		});
		//when
		this.toTest.prepare();
		//then
		assertFalse(this.headerSegment.getChildren().isEmpty());
		assertTrue(this.headerSegment.getChildren().size() == 1);
		assertEquals(this.headerSegment.getChildren().values().toArray()[0], this.dummySegment);
	}

	@Test
	public void testThatPrepareWorksForNotRootScopeLongPath() throws Exception {
		//given
		when(this.pageSegmentScopeContext.getSegments()).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(PagePreparatorTest.this.dummierSegment);
			}
		});
		when(this.pageSegmentScopeContext.getScopeName()).thenReturn("header.dummy.dummier");
		when(this.pageSegmentHolder.fetchPageSegments("header.dummy")).thenReturn(new ArrayList<PageSegment>() {
			{
				this.add(PagePreparatorTest.this.dummySegment);
			}
		});
		//when
		this.toTest.prepare();
		//then
		assertFalse(this.dummySegment.getChildren().isEmpty());
		assertTrue(this.dummySegment.getChildren().size() == 1);
		assertEquals(this.dummySegment.getChildren().values().toArray()[0], this.dummierSegment);
	}
}
