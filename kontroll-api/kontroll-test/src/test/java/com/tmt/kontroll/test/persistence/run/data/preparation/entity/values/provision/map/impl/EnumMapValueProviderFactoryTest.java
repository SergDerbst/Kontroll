package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public class EnumMapValueProviderFactoryTest {

	public enum DummyEnum {}
	public static class Dummy {
		public DummyEnum dummyEnumField;
	}

	@Mock
	private ValueProvisionHandler valueProvisionHandler;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testThatCreateWorks() throws Exception {
		//when
		final EnumMapValueProvider<DummyEnum> created = EnumMapValueProviderFactory.instance().create(this.valueProvisionHandler, DummyEnum.class);
		//then
		assertNotNull(created);
		assertTrue(created.canProvideValue(ValueProvisionKind.TwoDimensional, Dummy.class, EnumMap.class, DummyEnum.class, String.class));
	}
}
