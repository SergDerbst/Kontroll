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

	private final SortedSet<Object> providedEntities;

	private EntityInstanceProvider() {
		this.providedEntities = new TreeSet<>(new EntityInstanceProvisionComparator());
	}

	public Set<Object> provideEntities(final Class<?> entityType) throws Exception {
		this.providedEntities.clear();
		final Object entity = this.entityRelationshipCollector().collect(entityType);
		this.provideValues(entity);
		return this.providedEntities;
	}

	private Object provideValues(final Object entity) {
		try {
			this.providedEntities.add(entity);
			for (final Field field : retrievePropertyFields(entity.getClass())) {
				field.setAccessible(true);
				if (this.fieldIsIgnoredForTest(field)) {
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
		if (isRelationshipField(field)) {
			this.handleRelationshipField(entity, field);
			return;
		}
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

	private void handleRelationshipField(final Object entity,
	                                     final Field field) {
		final EntityRelationship relationship = this.entityRelationshipPool().retrieveRelationshipByEntity(entity);
		this.handleRelationshipEntity(isOwningRelationshipField(field) ? relationship.relatingEntity() : relationship.owningEntity());
	}

	private void handleRelationshipEntity(final Object entity) {
		if (!this.providedEntities.contains(entity)) {
			this.provideValues(entity);
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
