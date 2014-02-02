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
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener.KontrollDbUnitTestContext;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.annotations.value.TestMethodAnnotationBasedValueProvisionPreparer;
import com.tmt.kontroll.test.persistence.run.data.DataSetHolder;
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

	public void beforeTestMethod(final KontrollDbUnitTestContext testContext, final Method testMethod) throws Exception {
		this.annotationBasedValueProvisionPreparer.prepare(testMethod);
		this.setupOrTearDown(testContext, testMethod.getAnnotation(PersistenceTestConfig.class));
	}

	public void afterTestMethod(final KontrollDbUnitTestContext testContext, final Method testMethod) throws Exception {
		this.verfiy(testContext);
		this.setupOrTearDown(testContext, testMethod.getAnnotation(PersistenceTestConfig.class));
	}

	@SuppressWarnings("unchecked")
	private void setupOrTearDown(final KontrollDbUnitTestContext testContext, final PersistenceTestConfig config) throws Exception {
		final Class<? extends PersistenceEntityDaoServiceTest<?,?,?,?>> testClass = (Class<? extends PersistenceEntityDaoServiceTest<?, ?, ?, ?>>) testContext.getTestClass();
		final IDatabaseConnection connection = testContext.getConnection();
		TestDataPreparationHandler.instance().prepare(config, retrieveEntityClassName(testClass));
		final DatabaseOperation operation= testStrategyToDatabaseOperationMap.get(config.testStrategy());
		operation.execute(connection, DataSetHolder.instance().getDataSetBefore());
	}

	private void verfiy(final KontrollDbUnitTestContext testContext) throws Exception {
		final IDatabaseConnection connection = testContext.getConnection();
		final IDataSet actualDataSet = connection.createDataSet();
		final IDataSet expectedDataSet = DataSetHolder.instance().getDataSetAfter();
		DatabaseAssertionMode.DEFAULT.getDatabaseAssertion().assertEquals(expectedDataSet, actualDataSet);
	}
}