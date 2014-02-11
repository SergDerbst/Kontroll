package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;

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
			if (this.isOppositeRelationshipType(relationship.relationshipType(), relationshipType)) {
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
			if (this.isOppositeRelationshipType(relationship.relationshipType(), relationshipType)) {
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

	private TestDataHolder testDataHolder() {
		return PersistenceTestContext.instance().testDataHolder();
	}

	private EntityInstanceProvider entityInstanceProvider() {
		return PersistenceTestContext.instance().entityInstanceProvider();
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
