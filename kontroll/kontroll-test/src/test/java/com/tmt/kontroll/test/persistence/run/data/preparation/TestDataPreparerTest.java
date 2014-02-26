package com.tmt.kontroll.test.persistence.run.data.preparation;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.building.TestDataSetBuilder;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityReferenceComparator;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityUpdateProvider;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;
import com.tmt.kontroll.test.persistence.run.utils.exceptions.NoTestPreparerFoundException;

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
public abstract class TestDataPreparerTest {

	@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"blaField", "blubField"})})
	public static class Dummy {
		@Column(length = 10)
		public String lengthConstraintField = "bla";
		@Column(nullable = false)
		public String nullConstraintField ="blubb";
		@Column(unique = true)
		public String uniqueConstraintField = "blubber";
		public String blaField = "rhabarber";
		public String blubField = "barbara";
	}

	@Mock
	protected PersistenceTestConfig config;
	@Mock
	protected PersistenceTestContext persistenceContext;
	@Mock
	protected EntityInstanceProvider entityInstanceProvider;
	@Mock
	protected EntityReferenceAsserter entityReferenceAsserter;
	@Mock
	protected EntityUpdateProvider entityUpdateProvider;
	@Mock
	protected TestDataHolder testDataHolder;
	@Mock
	protected TestDataSetBuilder testDataSetBuilder;
	@Mock
	protected TestDataPreparer nextPreparer;

	protected EntityReference primaryReference;
	protected EntityReference nonPrimaryReference;
	protected Set<EntityReference> references;

	protected final TestDataPreparer toTest;
	protected final TestStrategy testStrategy;

	protected TestDataPreparerTest(final TestDataPreparer toTest,
	                               final TestStrategy testStrategy) {
		this.toTest = toTest;
		this.testStrategy = testStrategy;
	}

	protected abstract void verifyReferencesAfterTest();

	@Before
	@SuppressWarnings("serial")
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(PersistenceTestContext.class);
		this.primaryReference = new EntityReference(new Dummy(), true, false);
		this.nonPrimaryReference = new EntityReference(new Dummy(), false, false);
		this.references = new TreeSet<EntityReference>(new EntityReferenceComparator()) {{
			this.add(TestDataPreparerTest.this.primaryReference);
			this.add(TestDataPreparerTest.this.nonPrimaryReference);
		}};
		when(PersistenceTestContext.newInstance()).thenReturn(this.persistenceContext);
		when(PersistenceTestContext.instance()).thenReturn(this.persistenceContext);
		when(this.persistenceContext.entityInstanceProvider()).thenReturn(this.entityInstanceProvider);
		when(this.persistenceContext.entityReferenceAsserter()).thenReturn(this.entityReferenceAsserter);
		when(this.persistenceContext.entityUpdateProvider()).thenReturn(this.entityUpdateProvider);
		when(this.persistenceContext.testDataHolder()).thenReturn(this.testDataHolder);
		when(this.persistenceContext.testDataSetBuilder()).thenReturn(this.testDataSetBuilder);
		when(this.testDataHolder.allReferences()).thenReturn(this.references);
		when(this.config.numberOfEntities()).thenReturn(1);
		this.toTest.setNextPreparer(this.nextPreparer);
	}

	@Test
	public void testThatPrepareWorks() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(this.testStrategy);
		//when
		this.toTest.prepare(this.config, Dummy.class.getName());
		//then
		verify(this.testDataHolder).setPrimaryEntityType(Dummy.class);
		verify(this.entityInstanceProvider).provideEntityReferences(Dummy.class, this.testStrategy);
		verify(this.entityInstanceProvider).provideValues();
		this.verifyReferencesAfterTest();
	}

	@Test
	public void testThatNextPreparerIsCalled() throws Exception {
		//given
		when(this.config.testStrategy()).thenReturn(TestStrategy.None);
		//when
		this.toTest.prepare(this.config, Dummy.class.getName());
		//then
		verify(this.nextPreparer).prepare(this.config, Dummy.class.getName());
	}

	@Test(expected = NoTestPreparerFoundException.class)
	public void testThatIfNextPreparerIsNullExceptionWillBeThrown() throws Exception {
		//given
		this.toTest.setNextPreparer(null);
		when(this.config.testStrategy()).thenReturn(TestStrategy.None);
		//when
		this.toTest.prepare(this.config, Dummy.class.getName());
	}
}
