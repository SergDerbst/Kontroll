package com.tmt.kontroll.test.persistence.run.data.preparation;

import com.tmt.kontroll.test.persistence.run.data.preparation.impl.CountTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.DeleteTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.ExistsTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.FindTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.SaveTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.UpdateTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl.LengthConstraintTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl.NullableConstraintTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl.UniqueConstraintsOnColumnTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl.UniqueConstraintsOnTableTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.exceptions.TestPreparationFailedException;

public class TestDataPreparationHandler {

	private static class InstanceHolder {
		public static TestDataPreparationHandler instance;
	}

	public static TestDataPreparationHandler newInstance() {
		InstanceHolder.instance = new TestDataPreparationHandler();
		return InstanceHolder.instance;
	}

	private TestDataPreparer firstPreparer;

	private TestDataPreparationHandler() {
		this.setUpTestDataPreparation();
	}

	public void setUpTestDataPreparation() {
		this.addTestDataPreparer(CountTestDataPreparer.newInstance());
		this.addTestDataPreparer(DeleteTestDataPreparer.newInstance());
		this.addTestDataPreparer(ExistsTestDataPreparer.newInstance());
		this.addTestDataPreparer(FindTestDataPreparer.newInstance());
		this.addTestDataPreparer(SaveTestDataPreparer.newInstance());
		this.addTestDataPreparer(UpdateTestDataPreparer.newInstance());
		this.addTestDataPreparer(UniqueConstraintsOnTableTestDataPreparer.newInstance());
		this.addTestDataPreparer(UniqueConstraintsOnColumnTestDataPreparer.newInstance());
		this.addTestDataPreparer(NullableConstraintTestDataPreparer.newInstance());
		this.addTestDataPreparer(LengthConstraintTestDataPreparer.newInstance());
	}

	public void prepare(final PersistenceTestConfig config,
	                    final String entityClassName) {
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