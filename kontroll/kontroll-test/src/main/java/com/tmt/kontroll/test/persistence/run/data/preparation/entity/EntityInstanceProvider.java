package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldsForValueProvision;
import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveTypeArgumentsOfField;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;

public class EntityInstanceProvider {

	private static class InstanceHolder {
		public static EntityInstanceProvider instance = new EntityInstanceProvider();
	}

	public static EntityInstanceProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new EntityInstanceProvider();
		}
		return InstanceHolder.instance;
	}

	public Object provide(final Class<?> entityClass) {
		try {
			final Object entity = entityClass.newInstance();
			for (final Field field : retrieveFieldsForValueProvision(entityClass)) {
				if (TestDataHolder.instance().getReferenceAsserter().getIgnoredFieldNames().contains(field.getName())) {
					continue;
				}
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
		final ValueProvisionHandler valueProvisionHandler = TestDataHolder.instance().fetchValueProvisionHandler();
		if (Collection.class.isAssignableFrom(fieldType)) {
			field.set(entity, valueProvisionHandler.provide(fieldName, fieldType, retrieveTypeArgumentsOfField(field, 0)));
		} else if (Map.class.isAssignableFrom(fieldType)) {
			field.set(entity, valueProvisionHandler.provide(fieldName, fieldType, retrieveTypeArgumentsOfField(field, 0), retrieveTypeArgumentsOfField(field, 1)));
		} else if (fieldType.isArray()) {
			field.set(entity, valueProvisionHandler.provide(fieldName, fieldType, fieldType.getComponentType()));
		}	else {
			field.set(entity, valueProvisionHandler.provide(fieldName, fieldType));
		}
	}
}
