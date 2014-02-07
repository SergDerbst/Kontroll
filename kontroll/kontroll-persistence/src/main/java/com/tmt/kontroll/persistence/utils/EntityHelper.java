package com.tmt.kontroll.persistence.utils;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrievePropertyFields;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class EntityHelper {

	@SuppressWarnings("unchecked")
	public static <E> E copyEntity(final E entity) {
		try {
			final Class<? extends Object> entityClass = entity.getClass();
			final E entityToUpdate = (E) entityClass.newInstance();
			for (final Field field : retrievePropertyFields(entityClass)) {
				if (field.getAnnotation(Id.class) != null) {
					continue;
				}
				field.setAccessible(true);
				field.set(entityToUpdate, field.get(entity));
			}
			return entityToUpdate;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <E> E updateEntity(final E entityToUpdate,
	                                 final E entityToUpdateFrom) {
		try {
			final Class<? extends Object> entityClass = entityToUpdate.getClass();
			for (final Field field : retrievePropertyFields(entityClass)) {
				if (field.getAnnotation(Id.class) != null) {
					continue;
				}
				field.setAccessible(true);
				field.set(entityToUpdate, field.get(entityToUpdateFrom));
			}
			return entityToUpdate;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <E, V> void updateField(final E entity,
	                                      final V value,
	                                      final Field field) throws Exception {
		field.setAccessible(true);
		field.set(entity, value);
	}

	public static <E> void nullifyField(final E entity,
	                                    final Field field) throws Exception {
		field.setAccessible(true);
		field.set(entity, null);
	}

	@SuppressWarnings("unchecked")
	public static <E, V> V retrieveFieldValue(final E entity,
	                                          final Field field) throws Exception {
		field.setAccessible(true);
		return (V) field.get(entity);
	}

	public static <E> Field retrieveFieldByColumnName(final E entity, final String columnName) {
		for (final Field field : retrievePropertyFields(entity.getClass())) {
			final Column column = field.getAnnotation(Column.class);
			if (column != null && column.name().equals(columnName)) {
				return field;
			}
			if (field.getName().equals(columnName)) {
				return field;
			}
		}
		throw new RuntimeException("Failed to retrieve field by column name: " + columnName + ", in entity: " + entity.getClass().getName());
	}

	public static <E> List<Field> retrieveFieldsWithNullableConstraint(final E entity) {
		final List<Field> fields = new ArrayList<>();
		for (final Field field : retrievePropertyFields(entity.getClass())) {
			final Column column = field.getAnnotation(Column.class);
			if (column != null && !column.nullable()) {
				fields.add(field);
				continue;
			}
		}
		return fields;
	}

	public static <E> List<Field> retrieveFieldsWithLengthConstraint(final E entity) {
		final List<Field> fields = new ArrayList<>();
		for (final Field field : retrievePropertyFields(entity.getClass())) {
			final Column column = field.getAnnotation(Column.class);
			if (String.class.equals(field.getType()) && column != null) {
				fields.add(field);
				continue;
			}
		}
		return fields;
	}

	public static <E> List<Field> retrieveFieldsWithUniqueConstraint(final E entity) {
		final List<Field> fields = new ArrayList<>();
		for (final Field field : retrievePropertyFields(entity.getClass())) {
			final Column column = field.getAnnotation(Column.class);
			if (column != null && !column.unique()) {
				fields.add(field);
				continue;
			}
		}
		return fields;
	}

	public static <E> boolean hasUniqueConstraintsOnTable(final E entity) {
		final Table table = entity.getClass().getAnnotation(Table.class);
		return table != null && table.uniqueConstraints().length > 0;
	}

	public static <E> UniqueConstraint[] retrieveUniqueConstraintsOnTable(final E entity) {
		final Table table = entity.getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.uniqueConstraints();
		}
		return new UniqueConstraint[0];
	}
}
