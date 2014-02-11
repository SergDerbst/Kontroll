package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isOwningRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelatingEntityType;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelationshipType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;

public class EntityRelationshipCollector {

	private static class InstanceHolder {
		public static EntityRelationshipCollector instance;
	}

	public static EntityRelationshipCollector newInstance() {
		InstanceHolder.instance = new EntityRelationshipCollector();
		return InstanceHolder.instance;
	}

	public EntityReference collect(final Class<?> entityType, final boolean isPrimary) throws Exception {
		return this.collect(new EntityReference(entityType.newInstance(), isPrimary));
	}

	private EntityReference collect(final EntityReference reference) throws Exception {
		for (final Field field : retrievePropertyFields(reference.getReferenceType())) {
			if (isRelationshipField(field)) {
				this.handleRelationship(reference, field);
			}
		}
		return reference;
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
				relationship = new EntityRelationship(relationshipType, reference, new EntityReference(relatingEntityType.newInstance(), false), this.entityRelationshipPool().size());
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
				relationship = new EntityRelationship(relationshipType, new EntityReference(owningEntityType.newInstance(), false), reference, this.entityRelationshipPool().size());
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
