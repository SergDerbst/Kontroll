package com.tmt.kontroll.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public abstract class ObjectDataTest<TESTOBJECT> {

	private final TESTOBJECT testObject;
	
	@SuppressWarnings("serial")
	private final List<String> fieldsExcludedByName = new ArrayList<String>(){{
		add("$jacocoData");
	}};
	private final Map<String, Object> fieldByNameReferenceMap = new HashMap<String, Object>();
	@SuppressWarnings("serial")
	private final Map<Class<?>, Object> fieldByTypeReferenceMap = new HashMap<Class<?>, Object>() {{
		put(Boolean.class, false);
		put(Boolean.TYPE, false);
		put(Byte.class, Byte.parseByte("1"));
		put(Byte.TYPE, Byte.parseByte("1"));
		put(Character.class, "s".charAt(0));
		put(Character.TYPE, "s".charAt(0));
		put(Float.class, 1.0f);
		put(Float.TYPE, 1.0f);
		put(Double.class, 1.0d);
		put(Double.TYPE, 1.0d);
		put(Integer.class, 1);
		put(Integer.TYPE, 1);
		put(Long.class, 1l);
		put(Long.TYPE, 1l);
		put(String.class, "dumbString");
	}};
	
	protected ObjectDataTest(final TESTOBJECT testObject) {
		this.testObject = testObject;
	}
	
	protected List<String> getFieldsExcludedByNameMap() {
		return this.fieldsExcludedByName;
	}
	
	protected Map<Class<?>, Object> getFieldByTypeReferenceMap() {
		return this.fieldByTypeReferenceMap;
	}

	protected Map<String, Object> getFieldByNameReferenceMap() {
		return this.fieldByNameReferenceMap;
	}
	
	@Test
	public void testThatFinalFieldsAreNotNullAndThatGettersDoReturnTheProperValues() throws Exception {
		for (final Field field : this.retrieveFieldsFromTestObject(true)) {
			if (this.fieldsExcludedByName.contains(field.getName())) {
				continue;
			}
			field.setAccessible(true);
			Object fieldValue = field.get(this.testObject);
			assertNotNull("Final field '" + field.getName() + "' is null", fieldValue);
			final Method getter = this.retrieveGetterForField(field);
			if (getter != null) {
				assertEquals("Getter of final field '" + field.getName() + "' does not return the field's value.", getter.invoke(this.testObject));
			}
		}
	}
	
	@Test
	public void testThatNonFinalFieldsHaveWorkingGettersAndSetters() throws Exception {
		for (final Field field : this.retrieveFieldsFromTestObject(false)) {
			if (this.fieldsExcludedByName.contains(field.getName())) {
				continue;
			}
			Object referenceValue = this.getReferenceValueForField(field);
			Method setter = this.retrieveSetterForField(field);
			Method getter = this.retrieveGetterForField(field);
			if (getter == null && setter == null) {
				throw new RuntimeException("No getter and setter found for non-final field '" + field.getName());
			}
			if (setter == null) {
				throw new RuntimeException("No setter found for non-final field '" + field.getName());
			}
			if (getter == null) {
				throw new RuntimeException("No getter found for non-final field '" + field.getName());
			}
			setter.invoke(this.testObject, referenceValue);
			assertEquals("Getter of non-final field '" + field.getName() + "' does not return the proper value.", referenceValue, getter.invoke(this.testObject));
		}
	}
	
	private Object getReferenceValueForField(final Field field) {
		final String fieldName = field.getName();
		Object referenceValue = this.fieldByNameReferenceMap.get(fieldName);
		if (referenceValue == null) {
			referenceValue = this.fieldByTypeReferenceMap.get(field.getType());
		}
		if (referenceValue == null) {
			throw new RuntimeException("No reference value found for field '" + fieldName + "' with type " + field.getType().getName());
		}
		return referenceValue;
	}
	
	private Method retrieveGetterForField(final Field field) {
		for (Method method : this.testObject.getClass().getMethods()) {
			if (method.getName().equals("get" + StringUtils.capitalize(field.getName()))) {
				return method;
			}
		}
		return null;
	}
	
	private Method retrieveSetterForField(final Field field) {
		for (Method method : this.testObject.getClass().getMethods()) {
			if (method.getName().equals("set" + StringUtils.capitalize(field.getName()))) {
				return method;
			}
		}
		return null;
	}
	
	private List<Field> retrieveFieldsFromTestObject(final boolean finalFields) {
		final List<Field> fieldList = new ArrayList<Field>();
		Class<?> currentClass = this.testObject.getClass();
		while(!currentClass.getSuperclass().equals(Object.class)) {
			for (final Field field : Arrays.asList(currentClass.getDeclaredFields())) {
				if (finalFields && Modifier.isFinal(field.getModifiers())) {
					fieldList.add(field);
				}
				if (!finalFields && !Modifier.isFinal(field.getModifiers())) {
					fieldList.add(field);
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		return fieldList;
	}
}
