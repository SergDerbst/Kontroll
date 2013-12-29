package com.tmt.kontroll.content.config;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ContentPropertiesTest {

	private ContentProperties toTest;

	@Before
	public void setUp() {
		this.toTest = new ContentProperties();
	}
	
	@Test
	public void testThatContentPropertiesAreInitializedCorrectly() {
		assertNotNull(this.toTest.getContentItemBasePackages());
		assertTrue(this.toTest.getContentItemBasePackages().isEmpty());
	}
}
