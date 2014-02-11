package com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;

/**
 * Test data preparer for tests of database constraints.
 * </p>
 * Constraints tests assume that at least one primary entity exists in the database. It will then
 * try to update these with properties that would violate the tested constraints. It then tests if
 * the proper error is being thrown. Therefore, the references list for {@link TestPhase#Running}
 * needs to contain these violating entities.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public abstract class ConstraintsTestDataPreparer extends TestDataPreparer {

	/**
	 * Handles the given entitiy, so that it will violate the constraint to be tested, when trying to
	 * update it during the test.
	 * 
	 * @param entity
	 * @param violatingReferences
	 * @throws Exception
	 */
	protected abstract void handleConstraintEntity(final Object entity, final List<EntityReference> violatingReferences) throws Exception;

	@Override
	protected void prepareReferenceEntitiesForRunning(final PersistenceTestConfig config,
	                                                  final List<Object> entities,
	                                                  final Class<?> primaryEntityClass) throws Exception {
		final List<EntityReference> violatingReferences = new ArrayList<>();
		for (final Object entity : entities) {
			if (entity.getClass().equals(primaryEntityClass)) {
				this.handleConstraintEntity(entity, violatingReferences);
			}
		}
		super.testDataHolder().addReferences(TestPhase.Running, violatingReferences);
	}
}
