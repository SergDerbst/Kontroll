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

/**
 * Special runner for using <a href="http://dbunit.sourceforge.net/">DBUnit</a> before and after
 * test methods in order to setup, verify and teardown the database before, at the end of, and
 * after the test with dynamically created data sets.
 * </p>
 * It uses Marc Philipp's brilliant <a href="https://github.com/marcphilipp/dbunit-datasetbuilder">
 * DBUnit Data Set Builder</a> to dynamically create data sets for tests and Phil Webb's excellent
 * <a href="http://springtestdbunit.github.io/spring-test-dbunit/"/>Spring Test DbUnit</a> to
 * dynamically setup, verify and tear down the database.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class KontrollDbUnitRunner {

	/**
	 * Performs the following operations before a test method is run:
	 * </p>
	 * <ul>
	 * <li>initialize the {@link PersistenceTestContext}</li>
	 * <li>prepare all test data using the {@link TestDataPreparationHandler}</li>
	 * <li>update all reference values using {@link TestDataHolder#updateReferencesValues}</li>
	 * <li>set up the database according to the {@link IDataSet} stored under {@link TestPhase#Setup}
	 * in the {@link TestDataHolder} after preparation</li>
	 * </ul>
	 * </p>
	 * It uses DBUnit's {@link DatabaseSequenceFilter} to assure that data rows are inserted in the proper
	 * sequence to avoid foreign key constraint violations.
	 * </p>
	 * 
	 * @param testContext
	 * @param testMethod
	 * @param testPhase
	 * @throws Exception
	 */
	public void beforeTestMethod(final KontrollDbUnitTestContext testContext,
	                             final Method testMethod) throws Exception {
		this.startTestingContext();
		this.setupOrTearDown(testContext, testMethod, TestPhase.Setup);
	}

	/**
	 * It performs the following operations after a test method has run:
	 * </p>
	 * <ul>
	 * <li>verify the database against the {@link IDataSet} stored under {@link TestPhase#Verification}
	 * in the {@link TestDataHolder}, unless verification is set to be omitted</li>
	 * <li>tear down the database according to the {@link IDataSet} stored under {@link TestPhase#TearDown}
	 * (usually an empty data set, containing all tables, but no rows)</li>
	 * </ul>
	 * </p>
	 * It uses DBUnit's {@link DatabaseSequenceFilter} to assure that data rows are deleted in the proper
	 * sequence to avoid foreign key constraint violations.
	 * </p>
	 * 
	 * @param testContext
	 * @param testMethod
	 * @param omitVerification
	 * @throws Exception
	 */
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
		final PersistenceTestConfig config = testMethod.getAnnotation(PersistenceTestConfig.class);
		if (TestPhase.Setup == testPhase) {
			this.testDataPreparationHandler().prepare(config, retrieveEntityClassName(testClass));
			this.testDataHolder().updateReferencesValues();
		}
		final IDatabaseConnection connection = testContext.getConnection();
		final DatabaseSequenceFilter tableSequenceFilter = new DatabaseSequenceFilter(connection);
		final DatabaseOperation operation= DatabaseOperation.CLEAN_INSERT;
		operation.execute(connection, new FilteredDataSet(tableSequenceFilter, this.testDataHolder().fetchDataSet(testPhase)));
	}

	private void verfiy(final KontrollDbUnitTestContext testContext) throws Exception {
		final IDatabaseConnection connection = testContext.getConnection();
		final IDataSet actualDataSet = connection.createDataSet();
		final IDataSet expectedDataSet = this.testDataHolder().fetchDataSet(TestPhase.Verification);
		DatabaseAssertionMode.NON_STRICT.getDatabaseAssertion().assertEquals(expectedDataSet, actualDataSet);
	}

	private TestDataHolder testDataHolder() {
		return PersistenceTestContext.instance().testDataHolder();
	}

	private TestDataPreparationHandler testDataPreparationHandler() {
		return PersistenceTestContext.instance().testDataPreparationHandler();
	}

	/**
	 * Instantiates the {@link PersistenceTestContext}.
	 * </p>
	 * <i>Note: </i>This method's visibility is only protected, so it can be overridden
	 * in tests.
	 * </p>
	 * @throws Excetion
	 */
	protected void startTestingContext() throws Exception {
		PersistenceTestContext.newInstance();
	}
}