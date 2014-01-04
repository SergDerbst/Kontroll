package com.tmt.kontroll.context.request.data.path.scanning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanningHelper;
import com.tmt.kontroll.test.ObjectDataTest;

public class PropertyPathScanningHelperTest extends ObjectDataTest<PropertyPathScanningHelper>{
	
	private static class TestClass extends PropertyPathScanningHelperTest {
		@SuppressWarnings("unused")
		private final HashMap<String, Integer> map = new HashMap<String, Integer>();
	}
	
	private enum TestEnum {}
	
	public PropertyPathScanningHelperTest() {
		super(new PropertyPathScanningHelper());
	}
	
	@Test
	public void testThatGenericParameterTypeIsReturnedProperly() throws Exception {
		assertEquals(String.class, PropertyPathScanningHelper.retrieveGenericParameterTypeOfField(TestClass.class, "map", 0));
		assertEquals(Integer.class, PropertyPathScanningHelper.retrieveGenericParameterTypeOfField(TestClass.class, "map", 1));
	}
	
	@Test(expected = RuntimeException.class)
	public void testThatGenericParameterTypeIsReturnedThrowsException() throws Exception {
		PropertyPathScanningHelper.retrieveGenericParameterTypeOfField(TestClass.class, "wurstBongo", 0);
	}
	
	@Test
	public void testThatPrimitiveTypeIsTerminal() {
		assertTrue(PropertyPathScanningHelper.isTerminalProperty(Integer.TYPE));
	}

	@Test
	public void testThatAnnotationTypeIsTerminal() {
		assertTrue(PropertyPathScanningHelper.isTerminalProperty(Component.class));
	}
	
	@Test
	public void testThatArrayTypeIsTerminal() {
		assertTrue(PropertyPathScanningHelper.isTerminalProperty(String[].class));
	}
	
	@Test
	public void testThatEnumTypeIsTerminal() {
		assertTrue(PropertyPathScanningHelper.isTerminalProperty(TestEnum.class));
	}
	
	@Test
	public void testThatTerminalTypeIsTerminal() {
		assertTrue(PropertyPathScanningHelper.isTerminalProperty(String.class));
	}
	
	@Test
	public void testThatDirectlyTerminalTypeIsTerminal() {
		PropertyPathScanningHelper.getDirectlyTerminalTypes().add(Map.class);
		assertTrue(PropertyPathScanningHelper.isTerminalProperty(Map.class));
	}
	
	@Test
	public void testThatAssignablyTerminalTypeIsTerminal() {
		PropertyPathScanningHelper.getAssignablyTerminalTypes().add(List.class);
		assertTrue(PropertyPathScanningHelper.isTerminalProperty(List.class));
	}
	
	@Test
	public void testThatAssignablyTerminalTypeIsNotTerminal() {
		PropertyPathScanningHelper.getAssignablyTerminalTypes().add(List.class);
		assertFalse(PropertyPathScanningHelper.isTerminalProperty(Set.class));
	}
}
