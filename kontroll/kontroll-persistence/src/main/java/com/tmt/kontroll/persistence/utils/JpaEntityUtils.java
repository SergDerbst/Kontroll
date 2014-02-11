package com.tmt.kontroll.persistence.utils;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveAnnotatedFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveTypeArgumentsOfField;
import static org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class JpaEntityUtils {

	@SuppressWarnings("serial")
	private static final Map<Class<? extends Annotation>, Class<? extends Annotation>> relationshipMapping = new HashMap<Class<? extends Annotation>, Class<? extends Annotation>>() {{
		this.put(OneToOne.class, OneToOne.class);
		this.put(OneToMany.class, ManyToOne.class);
		this.put(ManyToOne.class, OneToMany.class);
		this.put(ManyToMany.class, ManyToMany.class);
	}};

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

	@SuppressWarnings("unchecked")
	public static <E, V> V retrieveIdValue(final E entity) throws Exception {
		final Field field = retrieveAnnotatedFields(entity.getClass(), Id.class).get(0);
		field.setAccessible(true);
		return (V) field.get(entity);
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

	public static <E> UniqueConstraint[] retrieveUniqueConstraintsOnTable(final E entity) {
		final Table table = entity.getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.uniqueConstraints();
		}
		return new UniqueConstraint[0];
	}

	public static <E> String retrieveColumnName(final Field field) {
		final Column column = field.getAnnotation(Column.class);
		if (column != null && !column.name().isEmpty()) {
			return column.name();
		}
		return field.getName();
	}

	public static boolean isNullableField(final Field field) {
		final Column column = field.getAnnotation(Column.class);
		return column != null && column.nullable();
	}

	public static boolean hasRelation(final Class<?> primaryEntityClass, final Class<?> relatingEntityClass) {
		for (final Field field : retrievePropertyFields(primaryEntityClass)) {
			if (isRelationshipField(field) && retrieveRelatingEntityType(field).equals(relatingEntityClass)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isRelationshipField(final Field field) {
		for (final Class<? extends Annotation> relationshipType : relationshipMapping.keySet()) {
			if (field.isAnnotationPresent(relationshipType)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isForeignKeyRelationshipField(final Field field) {
		if (field.isAnnotationPresent(OneToOne.class)) {
			return field.getAnnotation(OneToOne.class).mappedBy().isEmpty();
		}
		return field.isAnnotationPresent(ManyToOne.class);
	}

	public static Class<? extends Annotation> retrieveRelationshipType(final Field field) {
		for (final Class<? extends Annotation> relationshipType : relationshipMapping.keySet()) {
			if (field.isAnnotationPresent(relationshipType)) {
				return relationshipType;
			}
		}
		return null;
	}

	public static Class<? extends Annotation> retrieveOpposingRelationshipType(final Class<? extends Annotation> relationshipType) {
		return relationshipMapping.get(relationshipType);
	}

	public static boolean isOwningRelationshipField(final Field field) {
		final Class<? extends Annotation> relationshipType = retrieveRelationshipType(field);
		if (ManyToOne.class.equals(relationshipType)) {
			return true;
		} else {
			return ((String) getAnnotationAttributes(field.getAnnotation(relationshipType)).get("mappedBy")).isEmpty();
		}
	}

	public static Class<?> retrieveRelatingEntityType(final Field field) {
		return Collection.class.isAssignableFrom(field.getType()) ? retrieveTypeArgumentsOfField(field, 0) : field.getType();
	}

	public static Field retrieveRelatingField(final Class<?> entityType,
	                                          final Class<?> relatingEntityType) {
		for (final Field field : retrievePropertyFields(entityType)) {
			if (isRelatingField(relatingEntityType, field)) {
				return field;
			}
		}
		return null;
	}

	private static boolean isRelatingField(final Class<?> relatintEntityType, final Field field) {
		if (Collection.class.isAssignableFrom(field.getType())) {
			return retrieveTypeArgumentsOfField(field, 0).equals(relatintEntityType);
		} else {
			return field.getType().equals(relatintEntityType);
		}
	}
}
