package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public class CountTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static CountTestDataPreparer instance = new CountTestDataPreparer();
	}

	public static CountTestDataPreparer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new CountTestDataPreparer();
		}
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Count == config.testStrategy();
	}
}
