package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.copyEntity;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isOwningRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelatingEntityType;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelationshipType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

public class EntityRelationshipCollector {

	private static class InstanceHolder {
		public static EntityRelationshipCollector instance;
	}

	public static EntityRelationshipCollector newInstance() {
		InstanceHolder.instance = new EntityRelationshipCollector();
		return InstanceHolder.instance;
	}

	public void collect(final Class<?> entityType, final TestStrategy testStrategy) throws Exception {
		final EntityReference reference = new EntityReference(entityType.newInstance(), !this.isCreateOrDeleteStrategy(testStrategy), true);
		this.collect(reference);
		if (this.isCreateOrDeleteStrategy(testStrategy)) {
			new EntityReference(copyEntity(reference.getEntity()), true, true);
		}
	}

	private boolean isCreateOrDeleteStrategy(final TestStrategy testStrategy) {
		return
		TestStrategy.Create == testStrategy ||
		TestStrategy.Delete == testStrategy;
	}

	private void collect(final EntityReference reference) throws Exception {
		for (final Field field : retrievePropertyFields(reference.getReferenceType())) {
			if (isRelationshipField(field)) {
				this.handleRelationship(reference, field);
			}
		}
	}

	private void handleRelationship(final EntityReference reference,
	                                final Field field) throws Exception {
		final Class<? extends Annotation> relationshipType = retrieveRelationshipType(field);
		if (isOwningRelationshipField(field)) {
			final Class<?> relatingEntityType = retrieveRelatingEntityType(field);
			EntityRelationship relationship = this.entityRelationshipPool().retrieveRelationshipByOwningEntityReference(relationshipType, reference);
			if (relationship == null && this.isSelfRelatingRelationship(reference.getEntity(), relatingEntityType)) {
				relationship = this.entityRelationshipPool().retrieveRelationshipByRelatingEntityReference(relationshipType, reference);
			}
			if (relationship == null) {
				relationship = new EntityRelationship(relationshipType, reference, new EntityReference(relatingEntityType.newInstance(), false, true), this.entityRelationshipPool().size());
				this.entityRelationshipPool().addEntityRelationship(relationship);
				this.collect(relationship.relatingEntityReference());
			}
		} else {
			final Class<?> owningEntityType = retrieveRelatingEntityType(field);
			EntityRelationship relationship = this.entityRelationshipPool().retrieveRelationshipByRelatingEntityReference(relationshipType, reference);
			if (relationship == null && this.isSelfRelatingRelationship(reference.getEntity(), owningEntityType)) {
				relationship = this.entityRelationshipPool().retrieveRelationshipByOwningEntityReference(relationshipType, reference);
			}
			if (relationship == null) {
				relationship = new EntityRelationship(relationshipType, new EntityReference(owningEntityType.newInstance(), false, true), reference, this.entityRelationshipPool().size());
				this.entityRelationshipPool().addEntityRelationship(relationship);
				this.collect(relationship.owningEntityReference());
			}
		}
	}

	private <E> boolean isSelfRelatingRelationship(final E entity, final Class<?> relatingEntityType) {
		return entity.getClass().equals(relatingEntityType);
	}

	private EntityRelationshipPool entityRelationshipPool() {
		return PersistenceTestContext.instance().entityRelationshipPool();
	}
}
