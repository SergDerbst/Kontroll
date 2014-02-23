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

/**
 * An entity relationship represents a relationship between two entities.
 * </p>
 * On creation it will relate the two entities passed to its constructor according to the
 * relationship type passed to it as well. It also implements {@link #equals}, {@link #hashCode},
 * and {@link Comparable} in order to manage existing relationships within the
 * {@link EntityRelationshipPool}.
 * </p>
 * <i>Note:</i> Equality of entities is based on their instances and not their values.
 * Therefore, two entities of an {@link EntityReference} instance may have the exact same values,
 * yet still are considered different, if their system identity hash codes differ.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class EntityRelationship implements Comparable<EntityRelationship> {

	@SuppressWarnings("serial")
	private static final Map<Class<?>, Class<?>> collectionInstancesMap = new HashMap<Class<?>, Class<?>>() {{
		this.put(List.class, ArrayList.class);
		this.put(Set.class, HashSet.class);
		this.put(SortedSet.class, TreeSet.class);
	}};

	private final Class<? extends Annotation> relationshipType;
	private final EntityReference owningEntityReference;
	private final EntityReference relatingEntityReference;
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

	public int index() {
		return this.index;
	}

	private void relateEntities(final EntityReference owningEntityReference,
	                            final EntityReference relatingEntityReference) throws Exception {
		final Field owningField = retrieveRelatingField(owningEntityReference.referenceType(), relatingEntityReference.referenceType());
		final Field relatingField = retrieveRelatingField(relatingEntityReference.referenceType(), owningEntityReference.referenceType());
		owningField.setAccessible(true);
		relatingField.setAccessible(true);
		owningField.set(owningEntityReference.entity(), this.preparateRelationshipValue(relatingEntityReference, owningField));
		relatingField.set(relatingEntityReference.entity(), this.preparateRelationshipValue(owningEntityReference, relatingField));
	}

	@SuppressWarnings("unchecked")
	private Object preparateRelationshipValue(final EntityReference reference, final Field field) throws Exception {
		if (Collection.class.isAssignableFrom(field.getType())) {
			final Collection<Object> collection = (Collection<Object>) (field.getType().isInterface() ? collectionInstancesMap.get(field.getType()).newInstance() : field.getType().newInstance());
			collection.add(reference.entity());
			return collection;
		}
		return reference.entity();
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
			final int indexCompared = this.index - other.index;
			if (indexCompared == 0) {
				final int owningCompared = this.compareObject(this.owningEntityReference.entity(), other.owningEntityReference().entity());
				if (owningCompared == 0) {
					return this.compareObject(this.relatingEntityReference.entity(), other.relatingEntityReference().entity());
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
		if (o1.getClass().equals(o2.getClass())) {
			return System.identityHashCode(o1) - System.identityHashCode(o2);
		}
		return o1.getClass().getName().compareTo(o2.getClass().getName());
	}
}