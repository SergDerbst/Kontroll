package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import java.util.List;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public class DeleteTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static DeleteTestDataPreparer instance = new DeleteTestDataPreparer();
	}

	public static DeleteTestDataPreparer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new DeleteTestDataPreparer();
		}
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Delete == config.testStrategy();
	}

	@Override
	protected void prepareDataSets(final List<Object> entities) throws Exception {
		super.getTestDataHolder().setDataSetBefore(super.buildDataSetBefore(entities));
		entities.remove(0);
		super.getTestDataHolder().setDataSetAfter(super.buildDataSetAfter(entities));
	}
}