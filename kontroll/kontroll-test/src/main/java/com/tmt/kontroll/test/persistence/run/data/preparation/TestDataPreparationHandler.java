package com.tmt.kontroll.test.persistence.run.data.preparation;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.CountTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.DeleteTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.ExistsTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.FindTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.SaveTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.UpdateTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.exceptions.TestPreparationFailedException;

public class TestDataPreparationHandler {

	private static class InstanceHolder {
		public static TestDataPreparationHandler instance = new TestDataPreparationHandler();
	}

	public static TestDataPreparationHandler instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new TestDataPreparationHandler();
		}
		return InstanceHolder.instance;
	}

	private TestDataPreparer firstPreparer;

	public TestDataPreparationHandler() {
		this.setUpTestDataPreparation();
	}

	public void setUpTestDataPreparation() {
		this.addTestDataPreparer(CountTestDataPreparer.instance());
		this.addTestDataPreparer(DeleteTestDataPreparer.instance());
		this.addTestDataPreparer(ExistsTestDataPreparer.instance());
		this.addTestDataPreparer(FindTestDataPreparer.instance());
		this.addTestDataPreparer(SaveTestDataPreparer.instance());
		this.addTestDataPreparer(UpdateTestDataPreparer.instance());
	}

	public void prepare(final PersistenceTestConfig config, final String entityClassName) {
		try {
			this.firstPreparer.prepare(config, entityClassName);
		} catch (final Exception e) {
			throw new TestPreparationFailedException(e);
		}
	}

	public void addTestDataPreparer(final TestDataPreparer testDataPreparer) {
		testDataPreparer.setNextPreparer(this.firstPreparer);
		this.firstPreparer = testDataPreparer;
	}
}