package com.tmt.kontroll.test.persistence.run.data.preparation;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.building.TestDataSetBuilder;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;
import com.tmt.kontroll.test.persistence.run.utils.exceptions.TestPreparationFailedException;

/**
 * <b><i>Note:</i></b>
 * </br>
 * If you encounter a <code>java.lang.VerifyError</code> babbling about some inconsistent stackmap frames,
 * please make sure to follow these <a href="http://blog.triona.de/development/jee/how-to-use-powermock-with-java-7.html">instructions</a>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PersistenceTestContext.class})
public class TestDataPreparationHandlerTest {

	@Mock
	private PersistenceTestConfig config;
	@Mock
	private PersistenceTestContext persistenceContext;
	@Mock
	private EntityInstanceProvider entityInstanceProvider;
	@Mock
	private EntityReferenceAsserter entityReferenceAsserter;
	@Mock
	private TestDataHolder testDataHolder;
	@Mock
	private TestDataSetBuilder testDataSetBuilder;

	private TestDataPreparationHandler toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(PersistenceTestContext.class);
		when(PersistenceTestContext.newInstance()).thenReturn(this.persistenceContext);
		when(PersistenceTestContext.instance()).thenReturn(this.persistenceContext);
		when(this.persistenceContext.entityInstanceProvider()).thenReturn(this.entityInstanceProvider);
		when(this.persistenceContext.entityReferenceAsserter()).thenReturn(this.entityReferenceAsserter);
		when(this.persistenceContext.testDataHolder()).thenReturn(this.testDataHolder);
		when(this.persistenceContext.testDataSetBuilder()).thenReturn(this.testDataSetBuilder);
		when(this.config.numberOfEntities()).thenReturn(1);
		this.toTest = TestDataPreparationHandler.newInstance();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyCount() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.Count);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.Count);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyCreate() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.Create);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.Create);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyDelete() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.Delete);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.Delete);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyExists() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.Exists);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.Exists);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyRead() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.Read);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.Read);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyUpdate() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.Update);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.Update);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyUniqueConstraintsOnColumn() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.UniqueConstraintsOnColumn);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.UniqueConstraintsOnColumn);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyUniqueConstraintsOnTable() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.UniqueConstraintsOnTable);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.UniqueConstraintsOnTable);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyLengthConstraint() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.LengthConstraint);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.LengthConstraint);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test
	public void testThatPrepareWorksForTestStrategyNullableConstraint() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.NullableConstraint);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(this.getClass());
		verify(this.entityInstanceProvider).provideEntityReferences(this.getClass(), TestStrategy.NullableConstraint);
		verify(this.entityInstanceProvider).provideValues();
	}

	@Test(expected = TestPreparationFailedException.class)
	public void testThatPrepareThrowsTestPreparationFailedException() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.None);
		//when
		this.toTest.prepare(this.config, this.getClass().getName());
	}
}
