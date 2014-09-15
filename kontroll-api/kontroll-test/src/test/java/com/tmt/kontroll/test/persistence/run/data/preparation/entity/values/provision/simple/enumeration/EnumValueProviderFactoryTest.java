package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public class EnumValueProviderFactoryTest {

	public enum DummyEnum {}
	public static class Dummy  {
		public DummyEnum dummyEnumField;
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
		final EnumValueProvider created = EnumValueProviderFactory.instance().create(this.valueProvisionHandler, DummyEnum.class);
		//then
		assertNotNull(created);
		assertTrue(created.canProvideValue(ValueProvisionKind.ZeroDimensional, Dummy.class, DummyEnum.class));
	}
}
