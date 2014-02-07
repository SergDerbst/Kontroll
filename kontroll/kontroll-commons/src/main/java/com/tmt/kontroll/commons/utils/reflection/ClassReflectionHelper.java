package com.tmt.kontroll.commons.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ClassReflectionHelper {

	public static Class<?> retrieveTypeArgumentsOfField(final Field field, final int numberOfGenericParameter) {
		return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[numberOfGenericParameter];
	}

	public static boolean hasConstantWithName(final Class<?> clazz, final String fieldName) {
		for (final Field field : retrieveConstantFields(clazz)) {
			if (field.getName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	public static List<Field> retrieveConstantFields(final Class<?> clazz) {
		final List<Field> fields = new ArrayList<Field>();
		for (final Field field : retrieveAllFields(clazz)) {
			if (!isFieldToBeProvided(field)) {
				fields.add(field);
			}
		}
		return fields;
	}

	public static List<Field> retrievePropertyFields(final Class<?> clazz) {
		final List<Field> fields = new ArrayList<Field>();
		for (final Field field : retrieveAllFields(clazz)) {
			if (isFieldToBeProvided(field)) {
				fields.add(field);
			}
		}
		return fields;
	}

	public static List<Field> retrieveAllFields(final Class<?> clazz) {
		final List<Field> fields = new ArrayList<Field>();
		Class<?> currentClass = clazz;
		while (!Object.class.equals(currentClass)) {
			for (final Field field : currentClass.getDeclaredFields()) {
				fields.add(field);
			}
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}

	public static List<Field> retrieveAnnotatedFields(final Class<?> objectClass, final Class<? extends Annotation> annotationClass) {
		final List<Field> fields = new ArrayList<>();
		for (final Field field : retrieveAllFields(objectClass)) {
			final Annotation annotation = field.getAnnotation(annotationClass);
			if (annotation != null && annotation.annotationType().equals(annotationClass)) {
				fields.add(field);
			}
		}
		return fields;
	}

	public static Object retrieveFieldValue(final String fieldName, final Object object) {
		try {
			for (final Field field : retrieveAllFields(object.getClass())) {
				if (field.getName().equals(fieldName)) {
					field.setAccessible(true);
					return field.get(object);
				}
			}
			throw new RuntimeException("No field value found for field name '" + fieldName + "' in object: " + object.toString());
		} catch (final RuntimeException e) {
			throw e;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isFieldToBeProvided(final Field field) {
		return !field.getName().startsWith("$") && !(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()));
	}
}
