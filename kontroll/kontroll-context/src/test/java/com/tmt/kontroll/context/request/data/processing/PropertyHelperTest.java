package com.tmt.kontroll.context.request.data.processing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.ObjectDataTest;

public class PropertyHelperTest extends ObjectDataTest<PropertyHelper>{
	
	private static class TestClass extends PropertyHelperTest {
		@SuppressWarnings("unused")
		private final HashMap<String, Integer> map = new HashMap<String, Integer>();
	}
	
	private enum TestEnum {}
	
	public PropertyHelperTest() {
		super(new PropertyHelper());
	}
	
	@Test
	public void testThatGenericParameterTypeIsReturnedProperly() throws Exception {
		assertEquals(String.class, PropertyHelper.retrieveGenericParameterTypeOfField(TestClass.class, "map", 0));
		assertEquals(Integer.class, PropertyHelper.retrieveGenericParameterTypeOfField(TestClass.class, "map", 1));
	}
	
	@Test(expected = RuntimeException.class)
	public void testThatGenericParameterTypeIsReturnedThrowsException() throws Exception {
		PropertyHelper.retrieveGenericParameterTypeOfField(TestClass.class, "wurstBongo", 0);
	}
	
	@Test
	public void testThatPrimitiveTypeIsTerminal() {
		assertTrue(PropertyHelper.isTerminalProperty(Integer.TYPE));
	}

	@Test
	public void testThatAnnotationTypeIsTerminal() {
		assertTrue(PropertyHelper.isTerminalProperty(Component.class));
	}
	
	@Test
	public void testThatArrayTypeIsTerminal() {
		assertTrue(PropertyHelper.isTerminalProperty(String[].class));
	}
	
	@Test
	public void testThatEnumTypeIsTerminal() {
		assertTrue(PropertyHelper.isTerminalProperty(TestEnum.class));
	}
	
	@Test
	public void testThatTerminalTypeIsTerminal() {
		assertTrue(PropertyHelper.isTerminalProperty(String.class));
	}
	
	@Test
	public void testThatDirectlyTerminalTypeIsTerminal() {
		PropertyHelper.getDirectlyTerminalTypes().add(Map.class);
		assertTrue(PropertyHelper.isTerminalProperty(Map.class));
	}
	
	@Test
	public void testThatAssignablyTerminalTypeIsTerminal() {
		PropertyHelper.getAssignablyTerminalTypes().add(List.class);
		assertTrue(PropertyHelper.isTerminalProperty(List.class));
	}
	
	@Test
	public void testThatAssignablyTerminalTypeIsNotTerminal() {
		PropertyHelper.getAssignablyTerminalTypes().add(List.class);
		assertFalse(PropertyHelper.isTerminalProperty(Set.class));
	}
}
