package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isForeignKeyRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveFieldValue;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.dbunit.dataset.IDataSet;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;

/**
 * This comparator compares entities according to their foreign key relationships,
 * in order to assure that entities that have foreign keys are added to the
 * {@link IDataSet} after their relating entities;
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class EntityReferenceComparator implements Comparator<EntityReference> {

	@Override
	public int compare(final EntityReference reference1, final EntityReference reference2) {
		try {
			if (reference1 == reference2) {
				return 0;
			}
			for (final Field field : retrievePropertyFields(reference1.referenceType())) {
				if (isForeignKeyRelationshipField(field)) {
					final Object relatingEntity = retrieveFieldValue(reference1.entity(), field);
					if (reference2.entity() == relatingEntity) {
						return 1;
					}
				}
			}
			for (final Field field : retrievePropertyFields(reference2.referenceType())) {
				if (isForeignKeyRelationshipField(field)) {
					final Object relatingEntity = retrieveFieldValue(reference2.entity(), field);
					if (reference1.entity() == relatingEntity) {
						return -1;
					}
				}
			}
			return System.identityHashCode(reference1) - System.identityHashCode(reference2);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
