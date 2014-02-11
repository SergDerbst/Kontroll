package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.nullifyField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.hasRelation;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveRelatingField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#Create}.
 * </p>
 * Save tests assume that no primary entity exists in the database, before they save at least
 * one. Therefore the references list for {@link TestPhase#Setup} must be free of any primary
 * entities.
 * </p>
 * Furthermore, save tests assume that entities to relate to primary entities already exist in
 * the database. These relating entities will have null values on fields that have a relationship
 * to the primary entities that are saved during the test.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class SaveTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static SaveTestDataPreparer instance;
	}

	public static SaveTestDataPreparer newInstance() {
		InstanceHolder.instance = new SaveTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Create == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForSetup(final PersistenceTestConfig config,
	                                                final List<EntityReference> references,
	                                                final Class<?> primaryEntityClass) throws Exception {
		final List<EntityReference> setupReferences = new ArrayList<>();
		for (final EntityReference reference : references) {
			if (!reference.isPrimary()) {
				if (hasRelation(reference.getReferenceType(), primaryEntityClass)) {
					final Field relatingField = retrieveRelatingField(reference.getReferenceType(), primaryEntityClass);
					nullifyField(reference.getEntity(), relatingField);
				}
				setupReferences.add(reference);
			}
		}
		this.prepareReferenceEntitiesForTestPhase(TestPhase.Setup, setupReferences, primaryEntityClass);
	}
}
