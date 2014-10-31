package com.tmt.kontroll.ui.page.layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.ui.exceptions.ScopeNotFoundException;
import com.tmt.kontroll.ui.page.Page;
import com.tmt.kontroll.ui.page.PageHolder;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;

public class PageSegmentHolderTest {

	@PageConfig(contexts = {@PageContext(scope = "dummy")})
	public static class DummyPageSegment extends PageSegment {
		@Override
		public String getScope() {
			return "dummy";
		}
	}

	@Mock
	PageHolder								pageHolder;

	private PageSegmentHolder	toTest;

	@Before
	public void setUp() {
		initMocks(this);
		this.toTest = new PageSegmentHolder();
		this.toTest.pageHolder = this.pageHolder;
	}

	@Test
	public void testThatAddPageSegmentWorksOnce() {
		//given
		final DummyPageSegment toBeAdded = new DummyPageSegment();
		//when
		this.toTest.addPageSegment(toBeAdded);
		//then
		verify(this.pageHolder).addPage(eq("/"), any(Page.class));
		assertEquals(1, this.toTest.fetchAllContexts().size());
	}

	@Test
	public void testThatAddPageSegmentWorksTwice() {
		//given
		final DummyPageSegment toBeAdded = new DummyPageSegment();
		//when
		this.toTest.addPageSegment(toBeAdded);
		this.toTest.addPageSegment(toBeAdded);
		//then
		verify(this.pageHolder, times(2)).addPage(eq("/"), any(Page.class));
		assertEquals(1, this.toTest.fetchAllContexts().size());
	}

	@Test
	public void testThatFetchPageSegmentWorks() throws Exception {
		//given
		final DummyPageSegment toBeAdded = new DummyPageSegment();
		this.toTest.addPageSegment(toBeAdded);
		//when
		final List<PageSegment> segments = this.toTest.fetchPageSegments("dummy");
		//then
		assertNotNull(segments);
		assertEquals(1, segments.size());
		assertEquals("dummy", segments.get(0).getScope());
	}

	@Test(expected = ScopeNotFoundException.class)
	public void testThatFetchPageSegmentThrowsException() throws Exception {
		//given
		final DummyPageSegment toBeAdded = new DummyPageSegment();
		this.toTest.addPageSegment(toBeAdded);
		//when
		this.toTest.fetchPageSegments("ugly sandwich");
	}
}
