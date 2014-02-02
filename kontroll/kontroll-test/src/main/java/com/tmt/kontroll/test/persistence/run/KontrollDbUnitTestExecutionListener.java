package com.tmt.kontroll.test.persistence.run;

import java.lang.reflect.Method;

import org.dbunit.database.IDatabaseConnection;
import org.springframework.test.context.TestContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.dataset.DataSetLoader;
import com.github.springtestdbunit.operation.DatabaseOperationLookup;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;

public class KontrollDbUnitTestExecutionListener extends DbUnitTestExecutionListener {

	@Override
	public void beforeTestMethod(final TestContext testContext) throws Exception {
		final PersistenceTestConfig config = testContext.getTestMethod().getAnnotation(PersistenceTestConfig.class);
		if (config != null) {
			new KontrollDbUnitRunner().beforeTestMethod(new KontrollDbUnitTestContext(testContext), config);
		} else {
			super.beforeTestMethod(testContext);
		}
	}

	@Override
	public void afterTestMethod(final TestContext testContext) throws Exception {
		final PersistenceTestConfig config = testContext.getTestMethod().getAnnotation(PersistenceTestConfig.class);
		if (config != null) {
			new KontrollDbUnitRunner().afterTestMethod(new KontrollDbUnitTestContext(testContext), config);
		} else {
			super.afterTestMethod(testContext);
		}
	}

	public static class KontrollDbUnitTestContext {

		private final TestContext testContext;

		public KontrollDbUnitTestContext(final TestContext testContext) {
			this.testContext = testContext;
		}

		public IDatabaseConnection getConnection() {
			return (IDatabaseConnection) this.testContext.getAttribute(CONNECTION_ATTRIBUTE);
		}

		public DataSetLoader getDataSetLoader() {
			return (DataSetLoader) this.testContext.getAttribute(DATA_SET_LOADER_ATTRIBUTE);
		}

		public DatabaseOperationLookup getDatbaseOperationLookup() {
			return (DatabaseOperationLookup) this.testContext.getAttribute(DATABASE_OPERATION_LOOKUP_ATTRIBUTE);
		}

		public Class<?> getTestClass() {
			return this.testContext.getTestClass();
		}

		public Method getTestMethod() {
			return this.testContext.getTestMethod();
		}

		public Throwable getTestException() {
			return this.testContext.getTestException();
		}
	}
}
