package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isOwningRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelatingEntityType;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelationshipType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;

public class EntityRelationshipCollector {

	private static class InstanceHolder {
		public static EntityRelationshipCollector instance;
	}

	public static EntityRelationshipCollector newInstance() {
		InstanceHolder.instance = new EntityRelationshipCollector();
		return InstanceHolder.instance;
	}

	public <E> E collect(final Class<E> entityType) throws Exception {
		return this.collect(entityType.newInstance());
	}

	private <E> E collect(final E entity) throws Exception {
		for (final Field field : retrievePropertyFields(entity.getClass())) {
			if (isRelationshipField(field)) {
				this.handleRelationship(entity, field);
			}
		}
		return entity;
	}

	private <E> void handleRelationship(final E entity,
	                                    final Field field) throws Exception {
		final Class<? extends Annotation> relationshipType = retrieveRelationshipType(field);
		if (isOwningRelationshipField(field)) {
			final Class<?> relatingEntityType = retrieveRelatingEntityType(field);
			EntityRelationship relationship = this.entityRelationshipPool().retrieveRelationshipByOwningEntity(relationshipType, entity);
			if (relationship == null) {
				relationship = new EntityRelationship(relationshipType, entity, relatingEntityType.newInstance(), this.entityRelationshipPool().size());
				this.entityRelationshipPool().addEntityRelationship(relationship);
				this.collect(relationship.relatingEntity());
			}
		} else {
			final Class<?> owningEntityType = retrieveRelatingEntityType(field);
			EntityRelationship relationship = this.entityRelationshipPool().retrieveRelationshipByRelatingEntity(relationshipType, entity);
			if (relationship == null) {
				relationship = new EntityRelationship(relationshipType, owningEntityType.newInstance(), entity, this.entityRelationshipPool().size());
				this.entityRelationshipPool().addEntityRelationship(relationship);
				this.collect(relationship.owningEntity());
			}
		}
	}

	private EntityRelationshipPool entityRelationshipPool() {
		return PersistenceTestContext.instance().entityRelationshipPool();
	}
}
