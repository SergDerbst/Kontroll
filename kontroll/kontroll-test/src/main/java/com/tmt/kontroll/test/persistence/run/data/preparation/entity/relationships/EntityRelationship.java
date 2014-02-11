package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelatingField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EntityRelationship implements Comparable<EntityRelationship> {

	@SuppressWarnings("serial")
	private static final Map<Class<?>, Class<?>> collectionInstancesMap = new HashMap<Class<?>, Class<?>>() {{
		this.put(List.class, ArrayList.class);
		this.put(Set.class, HashSet.class);
		this.put(SortedSet.class, TreeSet.class);
	}};

	private final Class<? extends Annotation> relationshipType;
	private final Object owningEntity;
	private final Object relatingEntity;
	/** The sole purpose of this field is to make sure that {@link #compareTo} remains consistent with {@link equals}. */
	private final int index;

	public EntityRelationship(final Class<? extends Annotation> relationshipType,
	                          final Object owningEntity,
	                          final Object relatingEntity,
	                          final int index) throws Exception {
		if (relationshipType == null) {
			throw new IllegalArgumentException("Relationship type must not be null.");
		}
		this.relationshipType = relationshipType;
		this.owningEntity = owningEntity;
		this.relatingEntity = relatingEntity;
		this.index = index;
		this.relateEntities(owningEntity, relatingEntity);
	}

	public Class<? extends Annotation> relationshipType() {
		return this.relationshipType;
	}

	public Object owningEntity() {
		return this.owningEntity;
	}

	public Object relatingEntity() {
		return this.relatingEntity;
	}

	public int index() {
		return this.index;
	}

	private void relateEntities(final Object owningEntity,
	                            final Object relatingEntity) throws Exception {
		final Field owningField = retrieveRelatingField(owningEntity.getClass(), relatingEntity.getClass());
		final Field relatingField = retrieveRelatingField(relatingEntity.getClass(), owningEntity.getClass());
		owningField.setAccessible(true);
		relatingField.setAccessible(true);
		owningField.set(owningEntity, this.preparateRelationshipValue(relatingEntity, owningField));
		relatingField.set(relatingEntity, this.preparateRelationshipValue(owningEntity, relatingField));
	}

	@SuppressWarnings("unchecked")
	private Object preparateRelationshipValue(final Object entity, final Field field) throws Exception {
		if (Collection.class.isAssignableFrom(field.getType())) {
			final Collection<Object> collection = (Collection<Object>) (field.getType().isInterface() ? collectionInstancesMap.get(field.getType()).newInstance() : field.getType().newInstance());
			collection.add(entity);
			return collection;
		}
		return entity;
	}

	@Override
	public boolean equals(final Object o) {
		if (o != null) {
			if (o instanceof EntityRelationship) {
				final EntityRelationship other = (EntityRelationship) o;
				if (this.relationshipType.equals(other.relationshipType())) {
					if (this.index == other.index()) {
						if (this.owningEntity == other.owningEntity()) {
							return this.relatingEntity == other.relatingEntity();
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.relationshipType).append(this.owningEntity).append(this.relatingEntity).append(this.index).build();
	}

	@Override
	public int compareTo(final EntityRelationship other) {
		if (this.relationshipType.equals(other.relationshipType())) {
			final int owningCompared = this.compareObject(this.owningEntity, other.owningEntity());
			final int indexCompared = this.index - other.index;
			if (indexCompared == 0) {
				if (owningCompared == 0) {
					return this.compareObject(this.relatingEntity, other.relatingEntity());
				}
				return owningCompared;
			}
			return indexCompared;
		}
		return this.relationshipType.getName().compareTo(other.relationshipType().getName());
	}

	private int compareObject(final Object o1, final Object o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null && o2 != null) {
			return -1;
		}
		if (o1 != null && o2 == null) {
			return 1;
		}
		return o1.getClass().getName().compareTo(o2.getClass().getName());
	}
}