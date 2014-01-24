package com.tmt.kontroll.test.persistence.dao.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.array.ArrayValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.MapValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

@Component
public class EntityInstanceProvider {

	@Autowired
	ArrayValueProvisionHandler arrayValueProvisionHandler;
	@Autowired
	CollectionValueProvisionHandler collectionValueProvisionHandler;
	@Autowired
	MapValueProvisionHandler mapValueProvisionHandler;
	@Autowired
	SimpleValueProvisionHandler simpleValueProvisionHandler;

	public Object provide(final Class<?> entityClass) {
		try {
			final Object entity = entityClass.newInstance();
			for (final Field field : this.retrieveFields(entityClass)) {
				this.setFieldValue(entity, field);
			}
			return entity;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setFieldValue(final Object entity, final Field field) throws Exception {
		field.setAccessible(true);
		final String fieldName = field.getName();
		final Class<?> fieldType = field.getType();
		if (Collection.class.isAssignableFrom(fieldType)) {
			field.set(entity, this.collectionValueProvisionHandler.provide(fieldName, fieldType, this.retrieveTypeArgumentsOfField(field, 0)));
		} else if (Map.class.isAssignableFrom(fieldType)) {
			field.set(entity, this.mapValueProvisionHandler.provide(fieldName, fieldType, this.retrieveTypeArgumentsOfField(field, 0), this.retrieveTypeArgumentsOfField(field, 1)));
		} else if (fieldType.isArray()) {
			field.set(entity, this.arrayValueProvisionHandler.provide(fieldName, fieldType.getComponentType()));
		}
		else {
			field.set(entity, this.simpleValueProvisionHandler.provide(fieldName, fieldType));
		}
	}

	private Class<?> retrieveTypeArgumentsOfField(final Field field, final int numberOfGenericParameter) {
		return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[numberOfGenericParameter];
	}

	private List<Field> retrieveFields(final Class<?> entityClass) {
		final List<Field> fields = new ArrayList<Field>();
		Class<?> currentClass = entityClass;
		while(!Object.class.equals(currentClass)) {
			for (final Field field : currentClass.getDeclaredFields()) {
				if (this.isFieldToBeProvided(field)) {
					fields.add(field);
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}

	private boolean isFieldToBeProvided(final Field field) {
		return !field.getName().startsWith("$") && !(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()));
	}
}
