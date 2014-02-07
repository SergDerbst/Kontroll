package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

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
