package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public class FindTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static FindTestDataPreparer instance = new FindTestDataPreparer();
	}

	public static FindTestDataPreparer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new FindTestDataPreparer();
		}
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Find == config.testStrategy();
	}
}
