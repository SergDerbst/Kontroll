package com.tmt.kontroll.test.persistence.run;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.removeFinalModifier;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tmt.kontroll.persistence.daos.BaseCrudDaoService;
import com.tmt.kontroll.persistence.entities.BaseEntity;
import com.tmt.kontroll.test.persistence.PersistenceBaseEntityDaoServiceTest;
import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener.KontrollDbUnitTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparationHandler;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

public class KontrollDbUnitRunnerTest {

	//dummy persistence classes and test classes
	public static class TestEntity extends BaseEntity {}
	public static interface TestEntityRepository extends JpaRepository<TestEntity, Integer>, JpaSpecificationExecutor<TestEntity> {}
	public static class TestEntityDaoService extends BaseCrudDaoService<TestEntityRepository, TestEntity> {
		@Override
		public TestEntityRepository getRepository() {return null;}
	}
	public static class TestEntityDaoServiceTest extends PersistenceBaseEntityDaoServiceTest<TestEntity, TestEntityRepository, TestEntityDaoService> {
		@Override
		protected TestEntityDaoService daoService() {return null;}
		@PersistenceTestConfig(testStrategy = TestStrategy.Count)
		public void testMethod(){}
	}

	@Mock
	private KontrollDbUnitTestContext kontrollDbUnitTestContext;
	@Mock
	private IDatabaseConnection connection;
	@Mock
	private TestDataHolder testDataHolder;
	@Mock
	private TestDataPreparationHandler testDataPreparationHandler;
	@Mock
	private IDataSet setupDataSet;
	@Mock
	private IDataSet tearDownDataSet;
	@Mock
	private IDataSet verificationDataSetActual;
	@Mock
	private IDataSet verificationDataSetExpected;
	@Mock
	private CompositeOperation cleanInsertOperation;

	private Method testMethod;
	private KontrollDbUnitRunner toTest;

	@Before
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void setUp() throws Exception {
		initMocks(this);
		when(this.kontrollDbUnitTestContext.getTestClass()).thenReturn((Class) TestEntityDaoServiceTest.class);
		when(this.kontrollDbUnitTestContext.getConnection()).thenReturn(this.connection);
		when(this.testDataHolder.fetchDataSetForTestPhase(TestPhase.Setup)).thenReturn(this.setupDataSet);
		when(this.testDataHolder.fetchDataSetForTestPhase(TestPhase.TearDown)).thenReturn(this.tearDownDataSet);
		when(this.testDataHolder.dataSetForVerification()).thenReturn(this.verificationDataSetExpected);
		when(this.setupDataSet.getTableNames()).thenReturn(new String[0]);
		when(this.tearDownDataSet.getTableNames()).thenReturn(new String[0]);
		when(this.verificationDataSetExpected.getTableNames()).thenReturn(new String[0]);
		this.mockDatabaseOperation();
		this.prepareMethod();
		this.prepareClassToTest();
	}

	@After
	public void tearDown() throws Exception {
		verify(this.cleanInsertOperation).execute(eq(this.connection), any(FilteredDataSet.class));
	}

	@Test
	public void testThatBeforeTestMethodWorks() throws Exception {
		//given
		when(this.connection.createDataSet()).thenReturn(this.setupDataSet);
		//when
		this.toTest.beforeTestMethod(this.kontrollDbUnitTestContext, this.testMethod);
		//then
		verify(this.kontrollDbUnitTestContext).getConnection();
		verify(this.testDataPreparationHandler).prepare(any(PersistenceTestConfig.class), eq(TestEntity.class.getName()));
		verify(this.testDataHolder).updateReferencesValues();
	}

	@Test
	public void testThatAfterTestMethodWorksWithVerification() throws Exception {
		//given
		when(this.connection.createDataSet()).thenReturn(this.tearDownDataSet);
		//when
		this.toTest.afterTestMethod(this.kontrollDbUnitTestContext, this.testMethod, false);
		//then
		verify(this.kontrollDbUnitTestContext, times(3)).getConnection();
		verify(this.testDataPreparationHandler, never()).prepare(any(PersistenceTestConfig.class), eq(TestEntity.class.getName()));
		verify(this.testDataHolder, never()).updateReferencesValues();
	}

	@Test
	public void testThatAfterTestMethodWorksWithoutVerification() throws Exception {
		//given
		when(this.connection.createDataSet()).thenReturn(this.tearDownDataSet);
		//when
		this.toTest.afterTestMethod(this.kontrollDbUnitTestContext, this.testMethod, true);
		//then
		verify(this.kontrollDbUnitTestContext, times(2)).getConnection();
		verify(this.testDataPreparationHandler, never()).prepare(any(PersistenceTestConfig.class), eq(TestEntity.class.getName()));
		verify(this.testDataHolder, never()).updateReferencesValues();
	}

	/**
	 * We have to instantiate and override a weird test version here, because of a bug that doesn't allow
	 * us to both use PowerMock.mockStatic and access the "testMethod" Method object at the same time.
	 * 
	 * See issue posted <a href="https://code.google.com/p/powermock/issues/detail?id=355">here</a>.
	 * @throws Exception
	 */
	private void prepareClassToTest() throws Exception {
		this.toTest = new KontrollDbUnitRunner() {
			@Override
			protected void startTestingContext() {
				try {
					super.startTestingContext();
					this.preparePersistenceTestContext(PersistenceTestContext.instance());
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
			private void preparePersistenceTestContext(final PersistenceTestContext persistenceTestContext) throws Exception {
				final Field testDataHolderField = persistenceTestContext.getClass().getDeclaredField("testDataHolder");
				final Field testDataPreparationHandlerField = persistenceTestContext.getClass().getDeclaredField("testDataPreparationHandler");
				removeFinalModifier(testDataHolderField);
				removeFinalModifier(testDataPreparationHandlerField);
				testDataHolderField.set(persistenceTestContext, KontrollDbUnitRunnerTest.this.testDataHolder());
				testDataPreparationHandlerField.set(persistenceTestContext, KontrollDbUnitRunnerTest.this.testDataPreparationHandler());
			}
		};
		this.toTest.startTestingContext();
	}

	private void mockDatabaseOperation() throws Exception {
		final Field cleanInsertOperationField = DatabaseOperation.class.getDeclaredField("CLEAN_INSERT");
		removeFinalModifier(cleanInsertOperationField);
		cleanInsertOperationField.set(null, this.cleanInsertOperation);
	}

	private void prepareMethod() throws Exception {
		this.testMethod = TestEntityDaoServiceTest.class.getMethod("testMethod");
	}

	private TestDataHolder testDataHolder() {
		return this.testDataHolder;
	}

	private TestDataPreparationHandler testDataPreparationHandler() {
		return this.testDataPreparationHandler;
	}

	public IDatabaseConnection connection() {
		return this.connection;
	}

	public IDataSet dataSet() {
		return this.verificationDataSetActual;
	}
}