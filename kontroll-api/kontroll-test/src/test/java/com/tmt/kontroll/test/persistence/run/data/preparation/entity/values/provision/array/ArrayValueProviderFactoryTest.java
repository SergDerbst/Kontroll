package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public class ArrayValueProviderFactoryTest {

	public static class Dummy {
		public String[] dummyArrayField;
	}

	@Mock
	private ValueProvisionHandler valueProvisionHandler;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void testThatCreateWorks() throws Exception {
		//when
		final ArrayValueProvider<String> created = ArrayValueProviderFactory.instance().create(this.valueProvisionHandler, String.class);
		//then
		assertNotNull(created);
		assertTrue(created.canProvideValue(ValueProvisionKind.OneDimensional, Dummy.class, String[].class, String.class));
	}
}
