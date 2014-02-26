package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveTypeArgumentsOfField;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public class EntityProvisionHelper {

	@SuppressWarnings("unchecked")
	public static <E, V> V value(final E entity, final Field field) throws Exception {
		field.setAccessible(true);
		return (V) field.get(entity);
	}

	public static <E> Class<?>[] valueProvisionTypes(final E entity, final Field field) {
		if (Collection.class.isAssignableFrom(field.getType())) {
			return new Class<?>[]{entity.getClass(), field.getType(), retrieveTypeArgumentsOfField(field, 0)};
		} else if (Map.class.isAssignableFrom(field.getType())) {
			return new Class<?>[]{entity.getClass(), field.getType(), retrieveTypeArgumentsOfField(field, 0), retrieveTypeArgumentsOfField(field, 1)};
		} else if (field.getType().isArray()) {
			return new Class<?>[]{entity.getClass(), field.getType(), field.getType().getComponentType()};
		}	else {
			return new Class<?>[]{entity.getClass(), field.getType()};
		}
	}

	public static ValueProvisionKind valueProvisionKind(final Field field) {
		if (field.isAnnotationPresent(Id.class)) {
			return ValueProvisionKind.Id;
		}
		else if (Map.class.isAssignableFrom(field.getType())) {
			return ValueProvisionKind.TwoDimensional;
		} else if (Collection.class.isAssignableFrom(field.getType()) || field.getType().isArray()) {
			return ValueProvisionKind.OneDimensional;
		} else {
			return ValueProvisionKind.ZeroDimensional;
		}
	}
}
