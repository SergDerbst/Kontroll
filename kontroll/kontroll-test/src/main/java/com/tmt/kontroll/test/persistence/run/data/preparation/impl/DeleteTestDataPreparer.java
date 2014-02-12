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
 * Delete tests assume that at least one primary entity exists in the database. They will thus
 * take the first, i.e. the one with the smallest id, and delete it. Hence for the verification
 * phase a list needs to be prepared that contains all other entities but the first primary one.
 * </p>
 * TODO: what about relating entities and cascading shit?
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class DeleteTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static DeleteTestDataPreparer instance;
	}

	public static DeleteTestDataPreparer newInstance() {
		InstanceHolder.instance = new DeleteTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Delete == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForVerification(final PersistenceTestConfig config,
	                                                       final Set<EntityReference> references,
	                                                       final Class<?> primaryEntityClass) {
		boolean deleted = false;
		final Set<EntityReference> deletedReferences = new TreeSet<>(new EntityReferenceComparator());
		for (final EntityReference reference : references) {
			if (this.isReferenceToBeDeleted(primaryEntityClass, deleted, reference)) {
				deleted = true;
				continue;
			}
			deletedReferences.add(reference);
		}
		super.prepareReferenceEntitiesForTestPhase(TestPhase.Verification, deletedReferences, primaryEntityClass);
	}

	private boolean isReferenceToBeDeleted(final Class<?> primaryEntityClass, final boolean deleted, final EntityReference reference) {
		return !deleted && reference.isPrimary() && reference.getReferenceType().equals(primaryEntityClass);
	}
}