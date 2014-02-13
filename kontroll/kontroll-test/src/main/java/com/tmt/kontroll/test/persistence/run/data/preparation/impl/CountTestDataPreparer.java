package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#Count}.
 * </p>
 * Since count tests don't manipulate any data, all entities must be present at all test phases.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class CountTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static CountTestDataPreparer instance;
	}

	public static CountTestDataPreparer newInstance() {
		InstanceHolder.instance = new CountTestDataPreparer();
		return InstanceHolder.instance;
	}

	private CountTestDataPreparer() {}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Count == config.testStrategy();
	}
}
