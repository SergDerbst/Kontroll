package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public class SaveTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static SaveTestDataPreparer instance = new SaveTestDataPreparer();
	}

	public static SaveTestDataPreparer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new SaveTestDataPreparer();
		}
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Save == config.testStrategy();
	}

	@Override
	protected void prepareDataSets(final List<Object> entities) throws Exception {
		super.getTestDataHolder().setDataSetBefore(super.buildDataSetBefore(new ArrayList<>()));
		super.getTestDataHolder().setDataSetAfter(super.buildDataSetAfter(entities));
	}
}
