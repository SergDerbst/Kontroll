package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrieveAnnotatedFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrievePropertyFields;

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestPreparationContext;
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
	public Object provideNewUpdated(final Object entity) {
		try {
			final Class<? extends Object> entityClass = entity.getClass();
			final Object entityToUpdate = entityClass.newInstance();
			for (final Field field : retrievePropertyFields(entityClass)) {
				if (this.ignoredFieldNames().contains(field.getName())) {
					continue;
				}
				this.setFieldValueUpdated(entityToUpdate, entity, field, true);
			}
			return entityToUpdate;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Provides an updated version of the given <code>entityToUpdate</code>. It will not create a
	 * new instance, instead it will use the values from the <code>entityToUpdateFrom</code>
	 * parameter and set the respective fields of <code>entityToUpdate</code> to be the same,
	 * ignoring the ones set to be ignored in the {@link EntityReferenceAsserter}.
	 * </p>
	 * It will throw an {@link IllegalArgumentException}, if the entities passed to this method are
	 * not of the same class.
	 * </p>
	 * This method is used for testing update operations.
	 * </p>
	 * 
	 * @param entityToUpdate
	 * @param entityToUpdateFrom
	 * @return
	 */
	public <E> E provideUpdated(final E entityToUpdate,
	                            final E entityToUpdateFrom) {
		try {
			final Class<? extends Object> entityClass = entityToUpdate.getClass();
			for (final Field field : retrievePropertyFields(entityClass)) {
				if (this.ignoredFieldNames().contains(field.getName())) {
					continue;
				}
				this.setFieldValueUpdated(entityToUpdate, entityToUpdateFrom, field, false);
			}
			return entityToUpdate;
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
			field.set(entityToUpdate, this.valueProvisionHandler().fetchNextValue(entityToUpdate, field.get(entityToUpdateFrom)));
		} else {
			field.set(entityToUpdate, field.get(entityToUpdateFrom));
		}
	}

	private Set<String> ignoredFieldNames() {
		return TestPreparationContext.instance().referenceAsserter().getIgnoredFieldNames();
	}

	private ValueProvisionHandler valueProvisionHandler() {
		return TestPreparationContext.instance().valueProvisionHandler();
	}
}
