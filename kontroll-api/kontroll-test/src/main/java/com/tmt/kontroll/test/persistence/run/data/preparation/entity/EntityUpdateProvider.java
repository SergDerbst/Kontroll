package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveAnnotatedFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityProvisionHelper.value;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityProvisionHelper.valueProvisionKind;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityProvisionHelper.valueProvisionTypes;

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Id;

import com.tmt.kontroll.persistence.utils.JpaEntityUtils;
import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class EntityUpdateProvider {

	private static class InstanceHolder {
		public static EntityUpdateProvider instance;
	}

	public static EntityUpdateProvider newInstance() {
		InstanceHolder.instance = new EntityUpdateProvider();
		return InstanceHolder.instance;
	}

	/**
	 * Provides an updated version of the given entity by creating a new instance of the entity.
	 * All field values, except the ones set to be ignored in the {@link EntityReferenceAsserter} are
	 * set to the respective next value by passing the entity's fields original values to
	 * {@link ValueProvisionHandler#fetchNextValue}.
	 * </p>
	 * This method is used for reference creation during the preparation phase of tests.
	 * </p>
	 * 
	 * @param entity
	 * @return
	 */
	public EntityReference provideNewUpdated(final EntityReference reference) {
		try {
			final Class<? extends Object> entityClass = reference.referenceType();
			final Object entityToUpdate = entityClass.newInstance();
			for (final Field field : retrievePropertyFields(entityClass)) {
				if (this.ignoredFieldNames().contains(field.getName())) {
					continue;
				}
				final boolean useNextValue = !JpaEntityUtils.isRelationshipField(field);
				this.setFieldValueUpdated(entityToUpdate, reference.entity(), field, useNextValue);
			}
			return new EntityReference(entityToUpdate, reference.isPrimary(), false);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <E> void setFieldValueUpdated(final E entityToUpdate,
	                                      final E entityToUpdateFrom,
	                                      final Field field,
	                                      final boolean useNextValue) throws Exception {
		field.setAccessible(true);
		for (final Field annotatedField : retrieveAnnotatedFields(entityToUpdateFrom.getClass(), Id.class)) {
			if (annotatedField.getName().equals(field.getName())) {
				field.set(entityToUpdate, field.get(entityToUpdateFrom));
				return;
			}
		}
		if (useNextValue) {
			field.set(entityToUpdate, this.valueProvisionHandler().provideNextValue(entityToUpdate, valueProvisionKind(field), value(entityToUpdateFrom, field), valueProvisionTypes(entityToUpdate, field)));
		} else {
			field.set(entityToUpdate, field.get(entityToUpdateFrom));
		}
	}

	private Set<String> ignoredFieldNames() {
		return PersistenceTestContext.instance().entityReferenceAsserter().ignoredFieldNames();
	}

	private ValueProvisionHandler valueProvisionHandler() {
		return PersistenceTestContext.instance().valueProvisionHandler();
	}
}
