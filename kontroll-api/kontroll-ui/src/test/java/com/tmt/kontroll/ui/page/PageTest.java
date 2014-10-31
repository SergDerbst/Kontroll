package com.tmt.kontroll.ui.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.management.annotations.Condition;
import com.tmt.kontroll.ui.page.management.contexts.PageSegmentOrdinalKey;

public class PageTest {

	private Page	toTest;

	@Before
	public void setUp() {
		this.toTest = new Page();
	}

	@Test
	public void testThatScopeNameIsReturned() {
		assertEquals("page", this.toTest.getScope());
	}

	@Test
	public void testThatChildrenIsEmptyAfterInstantiation() {
		assertFalse(this.toTest.hasChildren());
		assertTrue(this.toTest.getChildren().isEmpty());
	}

	@Test
	public void testThatHasChildrenWorksAfterAdditionOfChild() {
		//when
		this.toTest.getChildren().put(new PageSegmentOrdinalKey(0, "page", new Condition[0], ConditionalOperator.And), new PageSegment() {
			@Override
			public String getScope() {
				return "scope";
			}
		});
		//then
		assertTrue(this.toTest.hasChildren());
	}
}
