package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import java.util.Set;
import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityReferenceComparator;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#Update}.
 * </p>
 * Update tests assume that after setup, there is at least one primary entity in the database to be updated
 * by the test. Therefore, the primary entity references for the {@link TestPhase#Running}
 * and {@link TestPhase#Verification} need to be updated.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 * 
 */
public class UpdateTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static UpdateTestDataPreparer instance;
	}

	public static UpdateTestDataPreparer newInstance() {
		InstanceHolder.instance = new UpdateTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Update == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForRunning(final PersistenceTestConfig config,
	                                                  final Set<EntityReference> references,
	                                                  final Class<?> primaryEntityClass) {
		this.prepareUpdatedReferenceEntities(references, TestPhase.Running, primaryEntityClass);
	}

	@Override
	protected void prepareReferenceEntitiesForVerification(final PersistenceTestConfig config,
	                                                       final Set<EntityReference> references,
	                                                       final Class<?> primaryEntityClass) {
		this.prepareUpdatedReferenceEntities(references, TestPhase.Verification, primaryEntityClass);
	}

	private void prepareUpdatedReferenceEntities(final Set<EntityReference> references,
	                                             final TestPhase testPhase,
	                                             final Class<?> primaryEntityClass) {
		final Set<EntityReference> updatedReferences = new TreeSet<>(new EntityReferenceComparator());
		for (final EntityReference reference : references) {
			if (reference.isPrimary()) {
				updatedReferences.add(super.entityUpdateProvider().provideNewUpdated(reference));
			}
		}
		super.prepareReferenceEntitiesForTestPhase(testPhase, updatedReferences, primaryEntityClass);
	}
}
