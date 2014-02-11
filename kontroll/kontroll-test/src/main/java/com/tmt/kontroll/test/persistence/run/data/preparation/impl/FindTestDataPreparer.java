package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#Read}.
 * </p>
 * Since find tests don't manipulate any data, all entities must be present at all test phases.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class FindTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static FindTestDataPreparer instance;
	}

	public static FindTestDataPreparer newInstance() {
		InstanceHolder.instance = new FindTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Read == config.testStrategy();
	}
}
