package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityProvisionHelper.valueProvisionKind;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityProvisionHelper.valueProvisionTypes;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships.EntityRelationshipCollector;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * The entity instance provider is responsible for instantiating instances of entities
 * with all field values set.
 * <p>
 * It first delegates the instantiation of entities to the {@link EntityRelationshipCollector},
 * which will instantiate all entity instances required to meet the relationship requirements
 * for the root entity to be provided here.
 * <p>
 * It then iterates over all fields and delegates them to the {@link ValueProvisionHandler} in
 * order to aquire values for them. It is assumed that all relationship fields have already been
 * set during entity relationship collection, so that their value provision is omitted.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
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
				for (final Field field : retrievePropertyFields(reference.referenceType())) {
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
		final ValueProvisionHandler valueProvisionHandler = this.valueProvisionHandler();
		if (isRelationshipField(field)) {
			return;
		}
		final Object entity = reference.entity();
		if (field.get(entity) == null) {
			field.set(entity, valueProvisionHandler.provide(entity, valueProvisionKind(field), valueProvisionTypes(entity, field)));
		}
	}

	private boolean fieldIsIgnoredForTest(final Field field) {
		return PersistenceTestContext.instance().entityReferenceAsserter().ignoredFieldNames().contains(field.getName());
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
