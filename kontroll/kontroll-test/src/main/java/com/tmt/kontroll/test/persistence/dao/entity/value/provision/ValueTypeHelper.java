package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ValueTypeHelper {

	public static Class<?> retrieveTypeArgumentsOfField(final Field field, final int numberOfGenericParameter) {
		return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[numberOfGenericParameter];
	}

	public static List<Field> retrieveFields(final Class<?> entityClass) {
		final List<Field> fields = new ArrayList<Field>();
		Class<?> currentClass = entityClass;
		while(!Object.class.equals(currentClass)) {
			for (final Field field : currentClass.getDeclaredFields()) {
				if (isFieldToBeProvided(field)) {
					fields.add(field);
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}

	private static boolean isFieldToBeProvided(final Field field) {
		return !field.getName().startsWith("$") && !(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()));
	}
}
