package com.tmt.kontroll.test.persistence.run;

import static com.tmt.kontroll.test.persistence.run.utils.PersistenceDaoEntityTestHelper.retrieveEntityClassName;

import java.util.EnumMap;
import java.util.Map;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.tmt.kontroll.test.persistence.dao.PersistenceEntityDaoServiceTest;
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener.KontrollDbUnitTestContext;
import com.tmt.kontroll.test.persistence.run.annotations.DbSetup;
import com.tmt.kontroll.test.persistence.run.dataset.KontrollDataSetLoaderAndTestReferencePreparator;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

public class KontrollDbUnitRunner {

	@SuppressWarnings("serial")
	private static final Map<TestStrategy, DatabaseOperation> testStrategyToDatabaseOperationMap = new EnumMap<TestStrategy, DatabaseOperation>(TestStrategy.class) {{
		this.put(TestStrategy.Provided, DatabaseOperation.CLEAN_INSERT);
	}};

	KontrollDataSetLoaderAndTestReferencePreparator dataSetLoader = KontrollDataSetLoaderAndTestReferencePreparator.instance();

	public void beforeTestMethod(final KontrollDbUnitTestContext testContext, final DbSetup dbSetup) throws Exception {
		this.setupOrTeardown(testContext, dbSetup);
	}

	@SuppressWarnings("unchecked")
	private void setupOrTeardown(final KontrollDbUnitTestContext testContext, final DbSetup dbSetup) throws Exception {
		final Class<? extends PersistenceEntityDaoServiceTest<?,?,?,?>> testClass = (Class<? extends PersistenceEntityDaoServiceTest<?, ?, ?, ?>>) testContext.getTestClass();
		final IDatabaseConnection connection = testContext.getConnection();
		final IDataSet dataSet = this.dataSetLoader.loadDataSetAndPrepareTestReference(testClass, retrieveEntityClassName(testClass), dbSetup.numberOfEntities());
		if (dataSet != null) {
			final DatabaseOperation operation= testStrategyToDatabaseOperationMap.get(dbSetup.strategy());
			operation.execute(connection, dataSet);
		}
	}
}