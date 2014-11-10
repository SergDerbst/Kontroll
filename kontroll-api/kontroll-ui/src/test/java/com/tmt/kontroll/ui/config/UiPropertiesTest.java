package com.tmt.kontroll.ui.config;


import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class UiPropertiesTest {

	private UiProperties toTest;
	
	@Before
	public void setUp() throws Exception {
		this.toTest = new UiProperties();
	}

	@Test
	public void testThatUiPropertiesAreInitializedCorrectly() {
		assertNotNull(this.toTest.basePackages());
	}
}
