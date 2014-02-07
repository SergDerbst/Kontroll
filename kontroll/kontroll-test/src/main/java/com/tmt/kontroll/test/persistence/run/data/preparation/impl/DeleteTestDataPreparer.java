package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import java.util.List;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

public class DeleteTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static DeleteTestDataPreparer instance;
	}

	public static DeleteTestDataPreparer newInstance() {
		InstanceHolder.instance = new DeleteTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Delete == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForVerification(final PersistenceTestConfig config,
	                                                       final List<Object> entities) {
		entities.remove(0);
		super.prepareReferenceEntitiesForTestPhase(TestPhase.Verification, entities);
	}
}