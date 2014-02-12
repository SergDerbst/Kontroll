package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveTypeArgumentsOfField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships.EntityRelationshipCollector;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

public class EntityInstanceProvider {

	private static class InstanceHolder {
		public static EntityInstanceProvider instance;
	}

	public static EntityInstanceProvider newInstance() {
		InstanceHolder.instance = new EntityInstanceProvider();
		return InstanceHolder.instance;
	}

	private EntityInstanceProvider() {}

	public void provideEntityReferences(final Class<?> entityType,
	                                    final TestStrategy testStrategy) throws Exception {
		this.entityRelationshipCollector().collect(entityType, testStrategy);
	}

	public void provideValues() {
		try {
			for (final EntityReference reference : this.testDataHolder().allReferences()) {
				for (final Field field : retrievePropertyFields(reference.getReferenceType())) {
					field.setAccessible(true);
					if (this.fieldIsIgnoredForTest(field)) {
						continue;
					}
					this.setFieldValue(reference, field);
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setFieldValue(final EntityReference reference,
	                           final Field field) throws Exception {
		final Class<?> fieldType = field.getType();
		final ValueProvisionHandler valueProvisionHandler = this.valueProvisionHandler();
		if (isRelationshipField(field)) {
			return;
		}
		final Object entity = reference.getEntity();
		if (field.get(entity) == null) {
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
	}

	private boolean fieldIsIgnoredForTest(final Field field) {
		return PersistenceTestContext.instance().entityReferenceAsserter().getIgnoredFieldNames().contains(field.getName());
	}

	private ValueProvisionHandler valueProvisionHandler() {
		return PersistenceTestContext.instance().valueProvisionHandler();
	}

	private EntityRelationshipCollector entityRelationshipCollector() {
		return PersistenceTestContext.instance().entityRelationshipCollector();
	}

	private TestDataHolder testDataHolder() {
		return PersistenceTestContext.instance().testDataHolder();
	}
}
