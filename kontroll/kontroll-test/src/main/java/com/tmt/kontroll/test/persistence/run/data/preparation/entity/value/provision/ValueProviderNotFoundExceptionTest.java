package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tmt.kontroll.test.ExceptionTest;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProviderNotFoundException;

public class ValueProviderNotFoundExceptionTest extends ExceptionTest {

	@SuppressWarnings("serial")
	public ValueProviderNotFoundExceptionTest() {
		super(new ArrayList<String>() {{
			this.add(ValueProviderNotFoundException.class.getPackage().getName());
		}});
	}

	@Test
	public void testThatExceptionIsPreparedForSimpleValueWithoutFieldName() {
		//when
		final ValueProviderNotFoundException prepared = ValueProviderNotFoundException.prepareWithTypes(String.class);
		//then
		assertFalse(prepared.getContextLabels().contains("field name"));
		assertTrue(prepared.getContextLabels().contains("value type"));
		assertEquals(String.class.getName(), prepared.getFirstContextValue("value type"));
	}

	@Test
	public void testThatExceptionIsPreparedForSimpleValueWithFieldName() {
		//when
		final ValueProviderNotFoundException prepared = ValueProviderNotFoundException.prepareWithTypes("blabla", String.class);
		//then
		assertTrue(prepared.getContextLabels().contains("field name"));
		assertEquals("blabla", prepared.getFirstContextValue("field name"));
		assertTrue(prepared.getContextLabels().contains("value type"));
		assertEquals(String.class.getName(), prepared.getFirstContextValue("value type"));
	}

	@Test
	public void testThatExceptionIsPreparedForArrayValueWithoutFieldName() {
		//when
		final ValueProviderNotFoundException prepared = ValueProviderNotFoundException.prepareWithTypes(Array.class, String.class);
		//then
		assertFalse(prepared.getContextLabels().contains("field name"));
		assertTrue(prepared.getContextLabels().contains("collection type"));
		assertEquals(Array.class.getName(), prepared.getFirstContextValue("collection type"));
		assertTrue(prepared.getContextLabels().contains("item type"));
		assertEquals(String.class.getName(), prepared.getFirstContextValue("item type"));
	}

	@Test
	public void testThatExceptionIsPreparedForArrayValueWithFieldName() {
		//when
		final ValueProviderNotFoundException prepared = ValueProviderNotFoundException.prepareWithTypes("blabla", Array.class, String.class);
		//then
		assertTrue(prepared.getContextLabels().contains("field name"));
		assertEquals("blabla", prepared.getFirstContextValue("field name"));
		assertTrue(prepared.getContextLabels().contains("collection type"));
		assertEquals(Array.class.getName(), prepared.getFirstContextValue("collection type"));
		assertTrue(prepared.getContextLabels().contains("item type"));
		assertEquals(String.class.getName(), prepared.getFirstContextValue("item type"));
	}

	@Test
	public void testThatExceptionIsPreparedForCollectionValueWithoutFieldName() {
		//when
		final ValueProviderNotFoundException prepared = ValueProviderNotFoundException.prepareWithTypes(List.class, String.class);
		//then
		assertFalse(prepared.getContextLabels().contains("field name"));
		assertTrue(prepared.getContextLabels().contains("collection type"));
		assertEquals(List.class.getName(), prepared.getFirstContextValue("collection type"));
		assertTrue(prepared.getContextLabels().contains("item type"));
		assertEquals(String.class.getName(), prepared.getFirstContextValue("item type"));
	}

	@Test
	public void testThatExceptionIsPreparedForCollectionValueWithFieldName() {
		//when
		final ValueProviderNotFoundException prepared = ValueProviderNotFoundException.prepareWithTypes("blabla", List.class, String.class);
		//then
		assertTrue(prepared.getContextLabels().contains("field name"));
		assertEquals("blabla", prepared.getFirstContextValue("field name"));
		assertTrue(prepared.getContextLabels().contains("collection type"));
		assertEquals(List.class.getName(), prepared.getFirstContextValue("collection type"));
		assertTrue(prepared.getContextLabels().contains("item type"));
		assertEquals(String.class.getName(), prepared.getFirstContextValue("item type"));
	}

	@Test
	public void testThatExceptionIsPreparedForMapValueWithoutFieldName() {
		//when
		final ValueProviderNotFoundException prepared = ValueProviderNotFoundException.prepareWithTypes(Map.class, String.class, Integer.class);
		//then
		assertFalse(prepared.getContextLabels().contains("field name"));
		assertTrue(prepared.getContextLabels().contains("map type"));
		assertEquals(Map.class.getName(), prepared.getFirstContextValue("map type"));
		assertTrue(prepared.getContextLabels().contains("key type"));
		assertEquals(String.class.getName(), prepared.getFirstContextValue("key type"));
		assertTrue(prepared.getContextLabels().contains("value type"));
		assertEquals(Integer.class.getName(), prepared.getFirstContextValue("value type"));
	}

	@Test
	public void testThatExceptionIsPreparedForMapValueWithFieldName() {
		//when
		final ValueProviderNotFoundException prepared = ValueProviderNotFoundException.prepareWithTypes("blabla", Map.class, String.class, Integer.class);
		//then
		assertTrue(prepared.getContextLabels().contains("field name"));
		assertEquals("blabla", prepared.getFirstContextValue("field name"));
		assertTrue(prepared.getContextLabels().contains("map type"));
		assertEquals(Map.class.getName(), prepared.getFirstContextValue("map type"));
		assertTrue(prepared.getContextLabels().contains("key type"));
		assertEquals(String.class.getName(), prepared.getFirstContextValue("key type"));
		assertTrue(prepared.getContextLabels().contains("value type"));
		assertEquals(Integer.class.getName(), prepared.getFirstContextValue("value type"));
	}
}
