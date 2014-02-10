package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EntityRelationship implements Comparable<EntityRelationship> {

	private final Class<? extends Annotation> relationshipType;
	private final Object owningEntity;
	private final Object relatingEntity;

	public EntityRelationship(final Class<? extends Annotation> relationshipType,
	                          final Object owningEntity,
	                          final Object relatingEntity) {
		if (relationshipType == null) {
			throw new IllegalArgumentException("Relationship type must not be null.");
		}
		this.relationshipType = relationshipType;
		this.owningEntity = owningEntity;
		this.relatingEntity = relatingEntity;
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

	@Override
	public boolean equals(final Object o) {
		if (o != null) {
			if (o instanceof EntityRelationship) {
				final EntityRelationship other = (EntityRelationship) o;
				if (this.relationshipType.equals(other.relationshipType())) {
					if (this.owningEntity == other.owningEntity()) {
						return this.relatingEntity == other.relatingEntity();
					}
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.relationshipType).append(this.owningEntity).append(this.relatingEntity).build();
	}

	@Override
	public int compareTo(final EntityRelationship other) {
		if (this.relationshipType.equals(other.relationshipType())) {
			final int owningCompared = this.compareObject(this.owningEntity, other.owningEntity());
			if (owningCompared == 0) {
				return this.compareObject(this.relatingEntity, other.relatingEntity());
			}
			return owningCompared;
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