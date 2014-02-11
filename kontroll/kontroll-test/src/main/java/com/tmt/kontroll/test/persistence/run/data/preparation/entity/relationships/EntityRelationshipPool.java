package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class EntityRelationshipPool {

	private static class InstanceHolder {
		public static EntityRelationshipPool instance;
	}

	public static EntityRelationshipPool newInstance() {
		InstanceHolder.instance = new EntityRelationshipPool();
		return InstanceHolder.instance;
	}

	private final Set<EntityRelationship> pool = new TreeSet<>();

	private EntityRelationshipPool() {}

	public void addEntityRelationship(final EntityRelationship entityRelationship) {
		this.pool.add(entityRelationship);
	}

	public EntityRelationship retrieveRelationshipByEntity(final Object entity) {
		for (final EntityRelationship relationship : this.pool) {
			if (relationship.owningEntity() == entity || relationship.relatingEntity() == entity) {
				return relationship;
			}
		}
		return null;
	}

	public EntityRelationship retrieveRelationshipByOwningEntity(final Class<? extends Annotation> relationshipType,
	                                                             final Object owningEntity) {
		for (final EntityRelationship relationship : this.pool) {
			if (this.isOppositeRelationshipType(relationship.relationshipType(), relationshipType)) {
				if (relationship.owningEntity() == owningEntity) {
					return relationship;
				}
			}
		}
		return null;
	}

	public EntityRelationship retrieveRelationshipByRelatingEntity(final Class<? extends Annotation> relationshipType,
	                                                               final Object relatingEntity) {
		for (final EntityRelationship relationship : this.pool) {
			if (this.isOppositeRelationshipType(relationship.relationshipType(), relationshipType)) {
				if (relationship.relatingEntity() == relatingEntity) {
					return relationship;
				}
			}
		}
		return null;
	}

	public int size() {
		return this.pool.size();
	}

	public void clear() {
		this.pool.clear();
	}

	private boolean isOppositeRelationshipType(final Class<? extends Annotation> r1, final Class<? extends Annotation> r2) {
		if (OneToMany.class.equals(r1)) {
			return ManyToOne.class.equals(r2);
		}
		if (ManyToOne.class.equals(r1)) {
			return OneToMany.class.equals(r2);
		}
		return r1.equals(r2);
	}
}
