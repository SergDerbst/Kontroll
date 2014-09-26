package com.tmt.kontroll.context.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ContextPropertiesTest {

	private ContextProperties toTest;
	
	@Before
	public void setUp() throws Exception {
		this.toTest = new ContextProperties();
	}

	@Test
	public void thatContextPropertiesAreInitializedCorrectly() {
		assertNotNull(this.toTest.requestContextServiceBasePackages());
		assertTrue(this.toTest.requestContextServiceBasePackages().isEmpty());
	}
}
