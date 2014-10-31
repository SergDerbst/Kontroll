package com.tmt.kontroll.ui.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.tmt.kontroll.ui.exceptions.PageNotFoundException;
import com.tmt.kontroll.ui.page.PageHolder;

public class PageHolderTest {

	private PageHolder	toTest;

	@Before
	public void setUp() {
		this.toTest = new PageHolder();
	}

	@Test
	public void testThatAddPageWorksOnce() {
		//given
		final Page page = new Page();
		//when
		this.toTest.addPage("/", page);
		//then
		final Page fetched = this.toTest.fetchPageByPattern("/");
		assertNotNull(fetched);
		assertEquals(page, fetched);
	}

	@Test
	public void testThatAddPageWorksTwice() {
		//given
		final Page page = new Page();
		//when
		this.toTest.addPage("/", page);
		this.toTest.addPage("/", page);
		//then
		final Page fetched = this.toTest.fetchPageByPattern("/");
		assertNotNull(fetched);
		assertEquals(page, fetched);
	}

	@Test
	public void testThatFetchPageByPathWorks() throws Exception {
		//given
		final Page page = new Page();
		this.toTest.addPage("/", page);
		//when
		final Page fetched = this.toTest.fetchPageByPath("/request");
		assertNotNull(fetched);
		assertEquals(page, fetched);
	}

	@Test(expected = PageNotFoundException.class)
	public void testThatFetchPageByPathThrowsExceptionForNoMatch() throws Exception {
		//given
		final Page page = new Page();
		this.toTest.addPage("/", page);
		//when
		this.toTest.fetchPageByPath("request");
	}

	@Test(expected = PageNotFoundException.class)
	public void testThatFetchPageByPathWorksThrowsExceptionForEmptyHolder() throws Exception {
		//when
		this.toTest.fetchPageByPath("/request");
	}
}
