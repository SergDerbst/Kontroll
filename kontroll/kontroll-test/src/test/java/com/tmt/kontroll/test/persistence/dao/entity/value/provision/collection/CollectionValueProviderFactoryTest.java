package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmt.kontroll.test.config.TestConfig;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CollectionValueProviderFactoryTest {

	@Autowired
	private SimpleValueProvisionHandler simpleValueProvisionHandler;

	@Autowired
	private CollectionValueProviderFactory toTest;

	@Before
	public void setUp() {
		this.simpleValueProvisionHandler.reset();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testThatListProviderIsCreatedProperly() {
		final CollectionValueProvider<Object, Collection<Object>> provider = this.toTest.create(List.class, String.class);
		assertNotNull(provider);
		final Object provided = provider.provide("", List.class, String.class);
		assertNotNull(provided);
		assertTrue(List.class.isAssignableFrom(provided.getClass()));
		assertEquals("0", ((List<String>) provided).get(0));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testThatSortedSetProviderIsCreatedProperly() {
		final CollectionValueProvider<Object, Collection<Object>> provider = this.toTest.create(SortedSet.class, String.class);
		assertNotNull(provider);
		final Object provided = provider.provide("", SortedSet.class, String.class);
		assertNotNull(provided);
		assertTrue(SortedSet.class.isAssignableFrom(provided.getClass()));
		assertEquals("0", ((SortedSet<String>) provided).first());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testThatSetProviderIsCreatedProperly() {
		final CollectionValueProvider<Object, Collection<Object>> provider = this.toTest.create(Set.class, String.class);
		assertNotNull(provider);
		final Object provided = provider.provide("", Set.class, String.class);
		assertNotNull(provided);
		assertTrue(Set.class.isAssignableFrom(provided.getClass()));
		assertEquals(1, ((Set<String>) provided).size());
		assertTrue(((Set<String>) provided).contains("0"));
	}

	@Test(expected = ValueProviderNotFoundException.class)
	public void testThatCreateThrowsValueProviderNotFoundException() {
		this.toTest.create(String.class, String.class);
	}
}
