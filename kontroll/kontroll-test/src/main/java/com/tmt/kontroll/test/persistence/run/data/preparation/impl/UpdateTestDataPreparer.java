package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public class UpdateTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static UpdateTestDataPreparer instance = new UpdateTestDataPreparer();
	}

	public static UpdateTestDataPreparer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new UpdateTestDataPreparer();
		}
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Update == config.testStrategy();
	}

	@Override
	protected void doPreparation(final PersistenceTestConfig config, final String entityClassName, final ValueProvisionHandler valueProvisionHandler) throws Exception {
		// TODO Auto-generated method stub
	}
}
