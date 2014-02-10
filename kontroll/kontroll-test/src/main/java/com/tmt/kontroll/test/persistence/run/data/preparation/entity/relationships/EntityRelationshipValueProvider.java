package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrieveRelatedField;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrieveTypeArgumentsOfField;

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

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tmt.kontroll.test.persistence.run.data.preparation.TestPreparationContext;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public abstract class EntityRelationshipValueProvider extends ValueProvider<Object> {

	@SuppressWarnings("serial")
	private static final Map<Class<?>, Class<?>> collectionInstancesMap = new HashMap<Class<?>, Class<?>>() {{
		this.put(List.class, ArrayList.class);
		this.put(Set.class, HashSet.class);
		this.put(SortedSet.class, TreeSet.class);
	}};

	private final Class<? extends Annotation> relationshipType;

	protected EntityRelationshipValueProvider(final ValueProvisionHandler provisionHandler,
	                                          final Class<? extends Annotation> relationshipType) {
		super(provisionHandler);
		this.relationshipType = relationshipType;
	}

	protected abstract boolean isOwningField(final Field field);

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return field != null && field.isAnnotationPresent(this.relationshipType);
	}

	@Override
	protected Object instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types)  throws Exception {
		if (this.isOwningField(field)) {
			final Class<?> relatingEntityType = Collection.class.isAssignableFrom(field.getType()) ? retrieveTypeArgumentsOfField(field, 0) : field.getType();
			EntityRelationship relationship = this.entityRelationshipPool().fetchEntityRelationshipByOwningEntity(this.relationshipType, entity);
			if (relationship == null) {
				relationship = new EntityRelationship(this.relationshipType, entity, relatingEntityType.newInstance());
				this.entityRelationshipPool().addEntityRelationship(relationship);
			}
			return this.retrieveValueObject(entity, relationship.relatingEntity(), field);
		} else {
			final Class<?> owningEntityType = Collection.class.isAssignableFrom(field.getType()) ? retrieveTypeArgumentsOfField(field, 0) : field.getType();
			EntityRelationship relationship = this.entityRelationshipPool().fetchEntityRelationshipByRelatingEntity(this.relationshipType, entity);
			if (relationship == null) {
				relationship = new EntityRelationship(this.relationshipType, entity, owningEntityType.newInstance());
				this.entityRelationshipPool().addEntityRelationship(relationship);
			}
			return this.retrieveValueObject(entity, relationship.owningEntity(), field);
		}
	}

	@Override
	protected Object makeNextDefaultValue(final Object entity, final Field field, final Object value) {
		return null;
	}

	private Object retrieveValueObject(final Object entity,
	                                   final Object valueEntity,
	                                   final Field field) throws Exception {
		final Field relatedField = retrieveRelatedField(valueEntity.getClass(), entity.getClass(), this.retrieveOppositeRelationshipType(this.relationshipType));
		relatedField.setAccessible(true);
		final Object value = relatedField.get(valueEntity);
		if (value == null) {
			/*
			 * we somehow need to break the cycle of relationships by setting the field value without propagating it to the
			 * value provision chain, yet still with setting all field values. maybe a new instance of the chain is required?
			 */
			final Object preparatedValue = this.preparateValue(field, this.entityInstanceProvider().provide(valueEntity));
			field.set(entity, preparatedValue);
			return value;
		} else {
			return this.preparateValue(field, value);
		}
	}

	private Class<? extends Annotation> retrieveOppositeRelationshipType(final Class<? extends Annotation> relationshipType) {
		if (OneToMany.class.equals(relationshipType)) {
			return ManyToOne.class;
		}
		if (ManyToOne.class.equals(relationshipType)) {
			return OneToMany.class;
		}
		return relationshipType;
	}

	@SuppressWarnings("unchecked")
	protected Object preparateValue(final Field field, final Object value) throws Exception {
		if (Collection.class.isAssignableFrom(field.getType())) {
			final Collection<Object> collection = (Collection<Object>) collectionInstancesMap.get(field.getType()).newInstance();
			collection.add(value);
			return collection;
		}
		return value;
	}

	protected EntityInstanceProvider entityInstanceProvider() {
		return TestPreparationContext.instance().entityInstanceProvider();
	}

	protected EntityRelationshipPool entityRelationshipPool() {
		return TestPreparationContext.instance().entityRelationshipPool();
	}
}
