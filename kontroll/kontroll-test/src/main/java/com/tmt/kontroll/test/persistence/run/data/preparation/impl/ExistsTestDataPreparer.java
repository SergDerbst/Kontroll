package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public class ExistsTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static ExistsTestDataPreparer instance = new ExistsTestDataPreparer();
	}

	public static ExistsTestDataPreparer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ExistsTestDataPreparer();
		}
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Exists == config.testStrategy();
	}
}
