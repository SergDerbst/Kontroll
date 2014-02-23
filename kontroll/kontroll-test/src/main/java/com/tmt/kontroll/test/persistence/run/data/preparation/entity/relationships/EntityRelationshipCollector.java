package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.copyEntity;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isOwningRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelatingEntityType;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelationshipType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * <p>
 * The entity relationship collector collects all entity relationships beginning with one
 * given entity type, usually the primary entity type of a persistence test.
 * </p>
 * <p>
 * It will check, if any field of the entity type marks a relationship to another entity type, and
 * if so, create a new instance of {@link EntityRelationship} with both entities related and add
 * it to the {@link EntityRelationshipPool} as follows:
 * </p>
 * <p>
 * <ul>
 * <li>instantiate an {@link EntityReference} for the primary entity</li>
 * <li>for all fields in the primary entity type that are relationship fields, it will:
 * <ul>
 * <li>check which side is owning and which is relating</li>
 * <li>if a relationship with this entity already exists:</li>
 * <ul>
 * <li>add a new {@link EntityReference} instance as relating or owning to the existing relationship</li>
 * </ul>
 * <li>otherwise:</li>
 * <ul>
 * <li>create a new relationship with the respective entity and entity reference and add it to the pool</li>
 * <li>make a recursive call to the collector for the newly created entity reference</li>
 * </ul>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * <p>
 * {@link TestStrategy#Create} and {@link TestStrategy#Delete} are handled differently from the rest. Since
 * their tests require to add or delete primary entities, their entity references will be added to the pool
 * as non-primary, while a copy of their instances will be added as primary to the {@link TestDataHolder} for
 * use in tests. This way it is assured that all relationships can exist in the databse and that the entities
 * used for tests are independent from those.
 * </p>
 * <p>
 * For general information on entity relationships in JPA, see
 * <a href="http://www.kumaranuj.com/2013/05/jpa-2-understanding-relationships.html">here</a>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class EntityRelationshipCollector {

	private static class InstanceHolder {
		public static EntityRelationshipCollector instance;
	}

	public static EntityRelationshipCollector newInstance() {
		InstanceHolder.instance = new EntityRelationshipCollector();
		return InstanceHolder.instance;
	}

	public void collect(final Class<?> entityType, final TestStrategy testStrategy) throws Exception {
		final EntityReference reference = new EntityReference(entityType.newInstance(), !this.isCreateOrDeleteStrategy(testStrategy), true);
		this.collect(reference);
		if (this.isCreateOrDeleteStrategy(testStrategy)) {
			new EntityReference(copyEntity(reference.entity()), true, true);
		}
	}

	private boolean isCreateOrDeleteStrategy(final TestStrategy testStrategy) {
		return
		TestStrategy.Create == testStrategy ||
		TestStrategy.Delete == testStrategy;
	}

	private void collect(final EntityReference reference) throws Exception {
		for (final Field field : retrievePropertyFields(reference.referenceType())) {
			if (isRelationshipField(field)) {
				this.handleRelationship(reference, field);
			}
		}
	}

	private void handleRelationship(final EntityReference reference,
	                                final Field field) throws Exception {
		final Class<? extends Annotation> relationshipType = retrieveRelationshipType(field);
		if (isOwningRelationshipField(field)) {
			final Class<?> relatingEntityType = retrieveRelatingEntityType(field);
			EntityRelationship relationship = this.entityRelationshipPool().retrieveRelationshipByOwningEntityReference(relationshipType, reference);
			if (relationship == null && this.isSelfRelatingRelationship(reference.entity(), relatingEntityType)) {
				relationship = this.entityRelationshipPool().retrieveRelationshipByRelatingEntityReference(relationshipType, reference);
			}
			if (relationship == null) {
				relationship = new EntityRelationship(relationshipType, reference, new EntityReference(relatingEntityType.newInstance(), false, true), this.entityRelationshipPool().size());
				this.entityRelationshipPool().addEntityRelationship(relationship);
				this.collect(relationship.relatingEntityReference());
			}
		} else {
			final Class<?> owningEntityType = retrieveRelatingEntityType(field);
			EntityRelationship relationship = this.entityRelationshipPool().retrieveRelationshipByRelatingEntityReference(relationshipType, reference);
			if (relationship == null && this.isSelfRelatingRelationship(reference.entity(), owningEntityType)) {
				relationship = this.entityRelationshipPool().retrieveRelationshipByOwningEntityReference(relationshipType, reference);
			}
			if (relationship == null) {
				relationship = new EntityRelationship(relationshipType, new EntityReference(owningEntityType.newInstance(), false, true), reference, this.entityRelationshipPool().size());
				this.entityRelationshipPool().addEntityRelationship(relationship);
				this.collect(relationship.owningEntityReference());
			}
		}
	}

	private <E> boolean isSelfRelatingRelationship(final E entity, final Class<?> relatingEntityType) {
		return entity.getClass().equals(relatingEntityType);
	}

	private EntityRelationshipPool entityRelationshipPool() {
		return PersistenceTestContext.instance().entityRelationshipPool();
	}
}
