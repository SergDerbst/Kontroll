package com.tmt.kontroll.test.persistence.run;

import static com.tmt.kontroll.test.persistence.run.utils.PersistenceDaoEntityTestHelper.retrieveEntityClassName;

import java.lang.reflect.Method;

import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.tmt.kontroll.test.persistence.PersistenceEntityDaoServiceTest;
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener.KontrollDbUnitTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparationHandler;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;

public class KontrollDbUnitRunner {

	public void beforeTestMethod(final KontrollDbUnitTestContext testContext,
	                             final Method testMethod) throws Exception {
		this.startTestingContext();
		this.setupOrTearDown(testContext, testMethod, TestPhase.Setup);
	}

	public void afterTestMethod(final KontrollDbUnitTestContext testContext,
	                            final Method testMethod,
	                            final boolean omitVerification) throws Exception {
		try {
			if (!omitVerification) {
				this.verfiy(testContext);
			}
			this.setupOrTearDown(testContext, testMethod, TestPhase.TearDown);
		} finally {
			testContext.getConnection().close();
		}
	}

	@SuppressWarnings("unchecked")
	private void setupOrTearDown(final KontrollDbUnitTestContext testContext,
	                             final Method testMethod,
	                             final TestPhase testPhase) throws Exception {
		final Class<? extends PersistenceEntityDaoServiceTest<?,?,?,?>> testClass = (Class<? extends PersistenceEntityDaoServiceTest<?, ?, ?, ?>>) testContext.getTestClass();
		final IDatabaseConnection connection = testContext.getConnection();
		final PersistenceTestConfig config = testMethod.getAnnotation(PersistenceTestConfig.class);
		if (TestPhase.Setup == testPhase) {
			this.testDataPreparationHandler().prepare(config, retrieveEntityClassName(testClass));
		}
		final DatabaseOperation operation= DatabaseOperation.CLEAN_INSERT;
		final DatabaseSequenceFilter tableSequenceFilter = new DatabaseSequenceFilter(connection);
		operation.execute(connection, new FilteredDataSet(tableSequenceFilter, this.testDataHolder().fetchDataSetForTestPhase(testPhase)));
	}

	private void verfiy(final KontrollDbUnitTestContext testContext) throws Exception {
		final IDatabaseConnection connection = testContext.getConnection();
		final IDataSet actualDataSet = connection.createDataSet();
		final IDataSet expectedDataSet = this.testDataHolder().dataSetForVerification();
		DatabaseAssertionMode.NON_STRICT.getDatabaseAssertion().assertEquals(expectedDataSet, actualDataSet);
	}

	private TestDataHolder testDataHolder() {
		return PersistenceTestContext.instance().testDataHolder();
	}

	private TestDataPreparationHandler testDataPreparationHandler() {
		return PersistenceTestContext.instance().testDataPreparationHandler();
	}

	private void startTestingContext() {
		PersistenceTestContext.newInstance();
	}
}