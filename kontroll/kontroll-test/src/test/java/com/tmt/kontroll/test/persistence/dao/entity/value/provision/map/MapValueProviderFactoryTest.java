package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmt.kontroll.test.config.TestConfig;
import com.tmt.kontroll.test.persistence.dao.entity.EntityInstanceProviderTest.TestEnum;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MapValueProviderFactoryTest {

	@Autowired
	private SimpleValueProvisionHandler simpleValueProvisionHandler;

	@Autowired
	private MapValueProviderFactory toTest;

	@Before
	public void setUp() throws Exception {
		this.simpleValueProvisionHandler.reset();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testThatDefaultMapValueProviderIsCreatedProperly() {
		final MapValueProvider<Object, Object, Map<Object, Object>> provider = this.toTest.create(Map.class, String.class, String.class);
		assertNotNull(provider);
		final Object provided = provider.provide("", Map.class, String.class, String.class);
		assertNotNull(provided);
		assertTrue(HashMap.class.isAssignableFrom(provided.getClass()));
		assertEquals(1, ((Map<String, String>) provided).size());
		assertTrue(((Map<String, String>) provided).keySet().contains("0"));
		assertTrue(((Map<String, String>) provided).values().contains("1"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testThatSortedMapValueProviderIsCreatedProperly() {
		final MapValueProvider<Object, Object, Map<Object, Object>> provider = this.toTest.create(SortedMap.class, String.class, String.class);
		assertNotNull(provider);
		final Object provided = provider.provide("", SortedMap.class, String.class, String.class);
		assertNotNull(provided);
		assertTrue(TreeMap.class.isAssignableFrom(provided.getClass()));
		assertEquals(1, ((Map<String, String>) provided).size());
		assertTrue(((Map<String, String>) provided).keySet().contains("0"));
		assertTrue(((Map<String, String>) provided).values().contains("1"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testThatEnumMapValueProviderIsCreatedProperly() {
		final MapValueProvider<Object, Object, Map<Object, Object>> provider = this.toTest.create(EnumMap.class, TestEnum.class, TestEnum.class);
		assertNotNull(provider);
		final Object provided = provider.provide("", EnumMap.class, TestEnum.class, TestEnum.class);
		assertNotNull(provided);
		assertTrue(EnumMap.class.isAssignableFrom(provided.getClass()));
		assertEquals(1, ((Map<String, String>) provided).size());
		assertTrue(((Map<String, String>) provided).keySet().contains(TestEnum.Bla));
		assertTrue(((Map<String, String>) provided).values().contains(TestEnum.Blubb));
	}
}
