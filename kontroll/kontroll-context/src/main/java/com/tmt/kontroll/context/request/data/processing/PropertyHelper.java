package com.tmt.kontroll.context.request.data.processing;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class PropertyHelper {
	
	@SuppressWarnings("serial")
	private static final List<Class<?>> directlyTerminalTypes = new ArrayList<Class<?>>() {{
		add(Boolean.class);
		add(Byte.class);
		add(Character.class);
		add(Double.class);
		add(Float.class);
		add(Integer.class);
		add(Long.class);
		add(Short.class);
		add(String.class);
	}};
	
	private static final List<Class<?>> assignablyTerminalTypes = new ArrayList<Class<?>>();

	public static Class<?> retrieveGenericParameterTypeOfField(final Class<?> classToProcess,
	                                                           final String propertyName,
	                                                           final int parameterNumber) {
		try {
			Class<?> currentClass = classToProcess;
			while (!currentClass.equals(Object.class)) {
				currentClass.getDeclaredFields();
				Field declaredField;
					declaredField = currentClass.getDeclaredField(propertyName);
				if (declaredField != null) {
					return (Class<?>) ((ParameterizedType) declaredField.getGenericType()).getActualTypeArguments()[parameterNumber];
				}
				currentClass = currentClass.getSuperclass();
			}
		} catch (	Exception e) {
			throw new RuntimeException("Field '" + propertyName + "' not found in class " + classToProcess.getName(), e);
		}
		return null;
	}
	
	public static boolean isTerminalProperty(Class<?> type) {
		return 
			type.isPrimitive() || 
			type.isAnnotation() ||
			type.isArray() ||
			type.isEnum() ||
			directlyTerminalTypes.contains(type) ||
			propertyTypeIsAssignablyTerminal(type);
	}
	
	private static boolean propertyTypeIsAssignablyTerminal(Class<?> type) {
		for (Class<?> c : assignablyTerminalTypes) {
			if (c.isAssignableFrom(type)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Class<?>> getDirectlyTerminalTypes() {
		return directlyTerminalTypes;
	}

	public static List<Class<?>> getAssignablyTerminalTypes() {
		return assignablyTerminalTypes;
	}
}