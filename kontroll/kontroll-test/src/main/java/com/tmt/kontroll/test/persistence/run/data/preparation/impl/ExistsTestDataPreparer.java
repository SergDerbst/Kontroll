package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#Exists}.
 * </p>
 * Since exists tests don't manipulate any data, all entities must be present at all test phases.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class ExistsTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static ExistsTestDataPreparer instance;
	}

	public static ExistsTestDataPreparer newInstance() {
		InstanceHolder.instance = new ExistsTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Exists == config.testStrategy();
	}
}
