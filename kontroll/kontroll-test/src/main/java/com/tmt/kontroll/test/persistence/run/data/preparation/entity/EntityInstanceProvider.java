package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrievePropertyFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrieveTypeArgumentsOfField;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.tmt.kontroll.test.persistence.run.data.preparation.TestPreparationContext;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class EntityInstanceProvider {

	private static class InstanceHolder {
		public static EntityInstanceProvider instance;
	}

	public static EntityInstanceProvider newInstance() {
		InstanceHolder.instance = new EntityInstanceProvider();
		return InstanceHolder.instance;
	}

	private EntityInstanceProvider() {}

	public Object provide(final Class<?> entityType) throws Exception {
		return this.provide(entityType.newInstance());
	}

	public Object provide(final Object entity) {
		try {
			for (final Field field : retrievePropertyFields(entity.getClass())) {
				field.setAccessible(true);
				if (this.fieldIsIgnoredForTest(field)) {
					continue;
				}
				if (field.get(entity) != null) {
					continue;
				}
				this.setFieldValue(entity, field);
			}
			return entity;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setFieldValue(final Object entity,
	                           final Field field) throws Exception {
		final Class<?> fieldType = field.getType();
		final ValueProvisionHandler valueProvisionHandler = this.valueProvisionHandler();
		if (Collection.class.isAssignableFrom(fieldType)) {
			field.set(entity, valueProvisionHandler.provide(entity, field, entity.getClass(), fieldType, retrieveTypeArgumentsOfField(field, 0)));
		} else if (Map.class.isAssignableFrom(fieldType)) {
			field.set(entity, valueProvisionHandler.provide(entity, field, entity.getClass(), fieldType, retrieveTypeArgumentsOfField(field, 0), retrieveTypeArgumentsOfField(field, 1)));
		} else if (fieldType.isArray()) {
			field.set(entity, valueProvisionHandler.provide(entity, field, entity.getClass(), fieldType, fieldType.getComponentType()));
		}	else {
			field.set(entity, valueProvisionHandler.provide(entity, field, entity.getClass(), fieldType));
		}
	}

	private boolean fieldIsIgnoredForTest(final Field field) {
		return TestPreparationContext.instance().referenceAsserter().getIgnoredFieldNames().contains(field.getName());
	}

	private ValueProvisionHandler valueProvisionHandler() {
		return TestPreparationContext.instance().valueProvisionHandler();
	}
}
