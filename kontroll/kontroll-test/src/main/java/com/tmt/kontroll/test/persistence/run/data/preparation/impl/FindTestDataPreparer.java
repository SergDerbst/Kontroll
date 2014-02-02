package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.IDataSet;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.reference.Reference;
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

	@Override
	protected void doPreparation(final PersistenceTestConfig config, final String entityClassName) throws Exception {
		final List<Object> entities = new ArrayList<>();
		for (int i = 0; i < config.numberOfEntities(); i++) {
			final Object entity = super.getEntityInstanceProvider().provide(Class.forName(entityClassName));
			super.getReferenceHolder().addReference(new Reference(entity));
			entities.add(entity);
		}
		final IDataSet dataSet = super.buildDataSet(entities);
		super.getDataSetHolder().setDataSetBefore(dataSet);
		super.getDataSetHolder().setDataSetAfter(dataSet);
	}
}
