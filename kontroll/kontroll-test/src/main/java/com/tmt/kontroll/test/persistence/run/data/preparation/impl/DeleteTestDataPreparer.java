package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
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
	                                                       final List<Object> entities,
	                                                       final Class<?> primaryEntityClass) {
		boolean deleted = false;
		final List<Object> deletedEntities = new ArrayList<>();
		for (final Object entity : entities) {
			if (!deleted && entity.getClass().equals(primaryEntityClass)) {
				deleted = true;
				continue;
			}
			deletedEntities.add(entity);
		}
		super.prepareReferenceEntitiesForTestPhase(TestPhase.Verification, deletedEntities, primaryEntityClass);
	}
}