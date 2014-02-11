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

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;

public class EntityRelationship implements Comparable<EntityRelationship> {

	@SuppressWarnings("serial")
	private static final Map<Class<?>, Class<?>> collectionInstancesMap = new HashMap<Class<?>, Class<?>>() {{
		this.put(List.class, ArrayList.class);
		this.put(Set.class, HashSet.class);
		this.put(SortedSet.class, TreeSet.class);
	}};

	private final Class<? extends Annotation> relationshipType;
	private EntityReference owningEntityReference;
	private EntityReference relatingEntityReference;
	/** The sole purpose of this field is to make sure that {@link #compareTo} remains consistent with {@link equals}. */
	private final int index;

	public EntityRelationship(final Class<? extends Annotation> relationshipType,
	                          final EntityReference owningEntityReference,
	                          final EntityReference relatingEntityReference,
	                          final int index) throws Exception {
		if (relationshipType == null) {
			throw new IllegalArgumentException("Relationship type must not be null.");
		}
		this.relationshipType = relationshipType;
		this.owningEntityReference = owningEntityReference;
		this.relatingEntityReference = relatingEntityReference;
		this.index = index;
		this.relateEntities(owningEntityReference, relatingEntityReference);
	}

	public Class<? extends Annotation> relationshipType() {
		return this.relationshipType;
	}

	public EntityReference owningEntityReference() {
		return this.owningEntityReference;
	}

	public EntityReference relatingEntityReference() {
		return this.relatingEntityReference;
	}

	public void setOwningEntityReference(final EntityReference owningEntityReference) {
		this.owningEntityReference = owningEntityReference;
	}

	public void setRelatingEntityReference(final EntityReference relatingEntityReference) {
		this.relatingEntityReference = relatingEntityReference;
	}

	public int index() {
		return this.index;
	}

	private void relateEntities(final EntityReference owningEntityReference,
	                            final EntityReference relatingEntityReference) throws Exception {
		final Field owningField = retrieveRelatingField(owningEntityReference.getReferenceType(), relatingEntityReference.getReferenceType());
		final Field relatingField = retrieveRelatingField(relatingEntityReference.getReferenceType(), owningEntityReference.getReferenceType());
		owningField.setAccessible(true);
		relatingField.setAccessible(true);
		owningField.set(owningEntityReference.getEntity(), this.preparateRelationshipValue(relatingEntityReference, owningField));
		relatingField.set(relatingEntityReference.getEntity(), this.preparateRelationshipValue(owningEntityReference, relatingField));
	}

	@SuppressWarnings("unchecked")
	private Object preparateRelationshipValue(final EntityReference reference, final Field field) throws Exception {
		if (Collection.class.isAssignableFrom(field.getType())) {
			final Collection<Object> collection = (Collection<Object>) (field.getType().isInterface() ? collectionInstancesMap.get(field.getType()).newInstance() : field.getType().newInstance());
			collection.add(reference.getEntity());
			return collection;
		}
		return reference.getEntity();
	}

	@Override
	public boolean equals(final Object o) {
		if (o != null) {
			if (o instanceof EntityRelationship) {
				final EntityRelationship other = (EntityRelationship) o;
				if (this.relationshipType.equals(other.relationshipType())) {
					if (this.index == other.index()) {
						if (this.owningEntityReference == other.owningEntityReference()) {
							return this.relatingEntityReference == other.relatingEntityReference();
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.relationshipType).append(this.owningEntityReference).append(this.relatingEntityReference).append(this.index).build();
	}

	@Override
	public int compareTo(final EntityRelationship other) {
		if (this.relationshipType.equals(other.relationshipType())) {
			final int owningCompared = this.compareObject(this.owningEntityReference, other.owningEntityReference());
			final int indexCompared = this.index - other.index;
			if (indexCompared == 0) {
				if (owningCompared == 0) {
					return this.compareObject(this.relatingEntityReference, other.relatingEntityReference());
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