package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

public class SaveTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static SaveTestDataPreparer instance;
	}

	public static SaveTestDataPreparer newInstance() {
		InstanceHolder.instance = new SaveTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Save == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForSetup(final PersistenceTestConfig config,
	                                                      final List<Object> entities) {
		this.prepareReferenceEntitiesForTestPhase(TestPhase.Setup, new ArrayList<>());
	}
}
