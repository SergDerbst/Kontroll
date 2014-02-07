package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

public class CountTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static CountTestDataPreparer instance;
	}

	public static CountTestDataPreparer newInstance() {
		InstanceHolder.instance = new CountTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Count == config.testStrategy();
	}
}
