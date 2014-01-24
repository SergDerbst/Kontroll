package com.tmt.kontroll.test.persistence.dao.entity.value.provision.array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmt.kontroll.test.config.TestConfig;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ArrayValueProviderFactoryTest {

	@Autowired
	private SimpleValueProvisionHandler simpleValueProvisionHandler;

	@Autowired
	private ArrayValueProviderFactory toTest;

	@Before
	public void setUp() throws Exception {
		this.simpleValueProvisionHandler.reset();
	}

	@Test
	public void testThatArrayValueProviderIsCreatedProperly() {
		final ArrayValueProvider<?> provider = this.toTest.create(String.class);
		assertNotNull(provider);
		final Object provided = provider.provide("", String.class);
		assertNotNull(provided);
		assertEquals(String[].class, provided.getClass());
		assertEquals("0", ((String[] )provided)[0]);
	}
}
