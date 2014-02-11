package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveTypeArgumentsOfField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isOwningRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships.EntityRelationship;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships.EntityRelationshipCollector;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships.EntityRelationshipPool;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class EntityInstanceProvider {

	private static class InstanceHolder {
		public static EntityInstanceProvider instance;
	}

	public static EntityInstanceProvider newInstance() {
		InstanceHolder.instance = new EntityInstanceProvider();
		return InstanceHolder.instance;
	}

	private final SortedSet<EntityReference> providedEntities;

	private EntityInstanceProvider() {
		this.providedEntities = new TreeSet<>(new EntityReferenceComparator());
	}

	public Set<EntityReference> provideEntityReferences(final Class<?> entityType) throws Exception {
		return this.provideEntityReferences(entityType, true);
	}

	public Set<EntityReference> provideEntityReferences(final Class<?> entityType, final boolean isPrimary) throws Exception {
		this.providedEntities.clear();
		final EntityReference reference = this.entityRelationshipCollector().collect(entityType, isPrimary);
		this.provideValues(reference);
		return this.providedEntities;
	}

	private EntityReference provideValues(final EntityReference reference) {
		try {
			this.providedEntities.add(reference);
			for (final Field field : retrievePropertyFields(reference.getReferenceType())) {
				field.setAccessible(true);
				if (this.fieldIsIgnoredForTest(field)) {
					continue;
				}
				this.setFieldValue(reference, field);
			}
			return reference;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setFieldValue(final EntityReference reference,
	                           final Field field) throws Exception {
		final Class<?> fieldType = field.getType();
		final ValueProvisionHandler valueProvisionHandler = this.valueProvisionHandler();
		if (isRelationshipField(field)) {
			this.handleRelationshipField(reference, field);
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

	private void handleRelationshipField(final EntityReference reference,
	                                     final Field field) {
		final EntityRelationship relationship = this.entityRelationshipPool().retrieveRelationshipByEntityReference(reference);
		this.handleRelationshipEntity(isOwningRelationshipField(field) ? relationship.relatingEntityReference() : relationship.owningEntityReference());
	}

	private void handleRelationshipEntity(final EntityReference reference) {
		if (!this.providedEntities.contains(reference)) {
			this.provideValues(reference);
		}
	}

	private boolean fieldIsIgnoredForTest(final Field field) {
		return PersistenceTestContext.instance().referenceAsserter().getIgnoredFieldNames().contains(field.getName());
	}

	private ValueProvisionHandler valueProvisionHandler() {
		return PersistenceTestContext.instance().valueProvisionHandler();
	}

	private EntityRelationshipCollector entityRelationshipCollector() {
		return PersistenceTestContext.instance().entityRelationshipCollector();
	}

	private EntityRelationshipPool entityRelationshipPool() {
		return PersistenceTestContext.instance().entityRelationshipPool();
	}
}
