package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;

/**
 * The entity relationship pool holds all {@link EntityRelationship} instances that have to
 * exist during one persistence test. Internally it uses a {@link TreeSet} to make sure that
 * no relationship is held twice. It offers several methods to retrieve a relationship based
 * on a given {@link EntityReference} instance.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
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

	public EntityRelationship retrieveRelationshipByEntityReference(final EntityReference reference) {
		for (final EntityRelationship relationship : this.pool) {
			if (relationship.owningEntityReference() == reference || relationship.relatingEntityReference() == reference) {
				return relationship;
			}
		}
		return null;
	}

	public EntityRelationship retrieveRelationshipByOwningEntityReference(final Class<? extends Annotation> relationshipType,
	                                                                      final EntityReference owningEntityReference) {
		for (final EntityRelationship relationship : this.pool) {
			if (this.isOppositeOrSameRelationshipType(relationship.relationshipType(), relationshipType)) {
				if (relationship.owningEntityReference() == owningEntityReference) {
					return relationship;
				}
			}
		}
		return null;
	}

	public EntityRelationship retrieveRelationshipByRelatingEntityReference(final Class<? extends Annotation> relationshipType,
	                                                                        final EntityReference relatingEntityReference) {
		for (final EntityRelationship relationship : this.pool) {
			if (this.isOppositeOrSameRelationshipType(relationship.relationshipType(), relationshipType)) {
				if (relationship.relatingEntityReference() == relatingEntityReference) {
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

	private boolean isOppositeOrSameRelationshipType(final Class<? extends Annotation> r1, final Class<? extends Annotation> r2) {
		if (OneToMany.class.equals(r1) && ManyToOne.class.equals(r2)) {
			return true;
		}
		if (ManyToOne.class.equals(r1) && OneToMany.class.equals(r2)) {
			return true;
		}
		return r1.equals(r2);
	}
}
