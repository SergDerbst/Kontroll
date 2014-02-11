package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isForeignKeyRelationshipField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveFieldValue;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.dbunit.dataset.IDataSet;

/**
 * This comparator compares entities according to their foreign key relationships,
 * in order to assure that entities that have foreign keys are added to the
 * {@link IDataSet} after their relating entities;
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class EntityInstanceProvisionComparator implements Comparator<Object> {

	@Override
	public int compare(final Object entity1, final Object entity2) {
		try {
			if (entity1 == entity2) {
				return 0;
			}
			for (final Field field : retrievePropertyFields(entity1.getClass())) {
				if (isForeignKeyRelationshipField(field)) {
					final Object relatingEntity = retrieveFieldValue(entity1, field);
					if (entity2 == relatingEntity) {
						return 1;
					}
				}
			}
			for (final Field field : retrievePropertyFields(entity2.getClass())) {
				if (isForeignKeyRelationshipField(field)) {
					final Object relatingEntity = retrieveFieldValue(entity2, field);
					if (entity1 == relatingEntity) {
						return -1;
					}
				}
			}
			return System.identityHashCode(entity1) - System.identityHashCode(entity2);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
