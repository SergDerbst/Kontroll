package com.tmt.kontroll.test.persistence.run;

import static com.tmt.kontroll.test.persistence.run.utils.PersistenceDaoEntityTestHelper.retrieveEntityClassName;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.tmt.kontroll.test.persistence.dao.PersistenceEntityDaoServiceTest;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener.KontrollDbUnitTestContext;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.annotations.value.TestMethodAnnotationBasedValueProvisionPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparationHandler;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public class KontrollDbUnitRunner {

	@SuppressWarnings("serial")
	private static final Map<TestStrategy, DatabaseOperation> testStrategyToDatabaseOperationMap = new EnumMap<TestStrategy, DatabaseOperation>(TestStrategy.class) {{
		this.put(TestStrategy.Count, DatabaseOperation.CLEAN_INSERT);
		this.put(TestStrategy.Delete, DatabaseOperation.CLEAN_INSERT);
		this.put(TestStrategy.Exists, DatabaseOperation.CLEAN_INSERT);
		this.put(TestStrategy.Find, DatabaseOperation.CLEAN_INSERT);
		this.put(TestStrategy.Save, DatabaseOperation.CLEAN_INSERT);
		this.put(TestStrategy.Update, DatabaseOperation.CLEAN_INSERT);
	}};

	TestMethodAnnotationBasedValueProvisionPreparer annotationBasedValueProvisionPreparer = TestMethodAnnotationBasedValueProvisionPreparer.instance();

	public void beforeTestMethod(final KontrollDbUnitTestContext testContext,
	                             final Method testMethod,
	                             final ValueProvisionHandler valueProvisionHandler) throws Exception {
		TestDataHolder.newInstance();
		this.annotationBasedValueProvisionPreparer.prepare(testMethod, valueProvisionHandler);
		this.setupOrTearDown(testContext, testMethod, valueProvisionHandler);
	}

	public void afterTestMethod(final KontrollDbUnitTestContext testContext,
	                            final Method testMethod,
	                            final ValueProvisionHandler valueProvisionHandler) throws Exception {
		this.verfiy(testContext);
		this.setupOrTearDown(testContext, testMethod, valueProvisionHandler);
	}

	@SuppressWarnings("unchecked")
	private void setupOrTearDown(final KontrollDbUnitTestContext testContext,
	                             final Method testMethod,
	                             final ValueProvisionHandler valueProvisionHandler) throws Exception {
		final Class<? extends PersistenceEntityDaoServiceTest<?,?,?,?>> testClass = (Class<? extends PersistenceEntityDaoServiceTest<?, ?, ?, ?>>) testContext.getTestClass();
		final IDatabaseConnection connection = testContext.getConnection();
		final PersistenceTestConfig config = testMethod.getAnnotation(PersistenceTestConfig.class);
		TestDataPreparationHandler.instance().prepare(config, retrieveEntityClassName(testClass));
		final DatabaseOperation operation= testStrategyToDatabaseOperationMap.get(config.testStrategy());
		operation.execute(connection, TestDataHolder.instance().getDataSetBefore());
	}

	private void verfiy(final KontrollDbUnitTestContext testContext) throws Exception {
		final IDatabaseConnection connection = testContext.getConnection();
		final IDataSet actualDataSet = connection.createDataSet();
		final IDataSet expectedDataSet = TestDataHolder.instance().getDataSetAfter();
		DatabaseAssertionMode.NON_STRICT.getDatabaseAssertion().assertEquals(expectedDataSet, actualDataSet);
	}
}