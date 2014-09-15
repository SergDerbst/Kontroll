package com.tmt.kontroll.test.persistence.run.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.building.TestDataSetBuilder;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityReferenceComparator;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;

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
public class TestDataHolderTest {

	@Mock
	private PersistenceTestContext persistenceTestContext;
	@Mock
	private EntityReference firstReference;
	@Mock
	private EntityReference secondReference;
	@Mock
	private EntityReference thirdReference;
	@Mock
	private EntityReferenceComparator comparator;
	@Mock
	private IDataSet setupDataSet;
	@Mock
	private IDataSet verificationDataSet;
	@Mock
	private IDataSet tearDownDataSet;
	@Mock
	private TestDataSetBuilder testDataSetBuilder;

	private Set<EntityReference> firstReferences;
	private Set<EntityReference> secondReferences;
	private Set<EntityReference> thirdReferences;

	private TestDataHolder toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(PersistenceTestContext.class);
		when(this.comparator.compare(this.firstReference, this.secondReference)).thenReturn(-1);
		when(this.comparator.compare(this.secondReference, this.firstReference)).thenReturn(1);
		when(this.comparator.compare(this.firstReference, this.thirdReference)).thenReturn(-1);
		when(this.comparator.compare(this.thirdReference, this.firstReference)).thenReturn(1);
		when(this.comparator.compare(this.secondReference, this.thirdReference)).thenReturn(-1);
		when(this.comparator.compare(this.thirdReference, this.secondReference)).thenReturn(1);
		when(PersistenceTestContext.instance()).thenReturn(this.persistenceTestContext);
		when(this.persistenceTestContext.testDataSetBuilder()).thenReturn(this.testDataSetBuilder);
		when(this.testDataSetBuilder.buildDataSetForSetup()).thenReturn(this.setupDataSet);
		when(this.testDataSetBuilder.buildDataSetForVerification()).thenReturn(this.verificationDataSet);
		when(this.testDataSetBuilder.buildDataSetForTearDown()).thenReturn(this.tearDownDataSet);
		this.firstReferences = this.prepareEntityReferences(this.firstReference);
		this.secondReferences = this.prepareEntityReferences(this.secondReference);
		this.thirdReferences = this.prepareEntityReferences(this.thirdReference);
		this.toTest = TestDataHolder.newInstance();
	}

	@After
	public void tearDown() {
		assertTrue(this.toTest.allReferences().isEmpty());
	}

	@Test
	public void testThatAddReferencesWorksOneNewTestPhase() {
		//given
		final TestPhase testPhase = TestPhase.Setup;
		//when
		this.toTest.addReferences(testPhase, this.firstReferences);
		//then
		assertTrue(this.toTest.fetchReferences(testPhase).contains(this.firstReference));
	}

	@Test
	public void testThatAddReferencesWorksOneExistingTestPhase() {
		//given
		final TestPhase testPhase = TestPhase.Setup;
		//when
		this.toTest.addReferences(testPhase, this.firstReferences);
		this.toTest.addReferences(testPhase, this.secondReferences);
		//then
		assertTrue(this.toTest.fetchReferences(testPhase).contains(this.firstReference));
		assertTrue(this.toTest.fetchReferences(testPhase).contains(this.secondReference));
	}

	@Test
	public void testThatUpdateReferencesValuesWorks() {
		//when
		this.toTest.addReferences(TestPhase.Setup, this.firstReferences);
		this.toTest.addReferences(TestPhase.Running, this.secondReferences);
		this.toTest.updateReferencesValues();
		//then
		verify(this.firstReference).updateReferenceValueMap();
		verify(this.secondReference).updateReferenceValueMap();
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatFetchPrimaryTypeReferencesWorks() {
		//given
		final TestPhase setup = TestPhase.Setup;
		when(this.firstReference.isPrimary()).thenReturn(true);
		when(this.secondReference.isPrimary()).thenReturn(false);
		when(this.thirdReference.isPrimary()).thenReturn(false);
		when(this.firstReference.referenceType()).thenReturn((Class) this.getClass());
		when(this.secondReference.referenceType()).thenReturn((Class) this.getClass());
		when(this.thirdReference.referenceType()).thenReturn((Class) String.class);
		this.toTest.addReferences(setup, this.firstReferences);
		this.toTest.addReferences(setup, this.secondReferences);
		this.toTest.addReferences(setup, this.thirdReferences);
		this.toTest.setPrimaryEntityType(this.getClass());
		//when
		final List<EntityReference> references = this.toTest.fetchPrimaryTypeReferences(setup);
		//then
		assertTrue(references.contains(this.firstReference));
		assertTrue(references.contains(this.secondReference));
		assertFalse(references.contains(this.thirdReference));
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatFetchPrimaryReferencesWorks() {
		//given
		final TestPhase setup = TestPhase.Setup;
		when(this.firstReference.isPrimary()).thenReturn(true);
		when(this.secondReference.isPrimary()).thenReturn(false);
		when(this.thirdReference.isPrimary()).thenReturn(false);
		when(this.firstReference.referenceType()).thenReturn((Class) this.getClass());
		when(this.secondReference.referenceType()).thenReturn((Class) this.getClass());
		when(this.thirdReference.referenceType()).thenReturn((Class) String.class);
		this.toTest.addReferences(setup, this.firstReferences);
		this.toTest.addReferences(setup, this.secondReferences);
		this.toTest.addReferences(setup, this.thirdReferences);
		this.toTest.setPrimaryEntityType(this.getClass());
		//when
		final List<EntityReference> references = this.toTest.fetchPrimaryReferences(setup);
		//then
		assertTrue(references.contains(this.firstReference));
		assertFalse(references.contains(this.secondReference));
		assertFalse(references.contains(this.thirdReference));
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatFetchNonPrimaryReferencesWorks() {
		//given
		final TestPhase setup = TestPhase.Setup;
		when(this.firstReference.isPrimary()).thenReturn(true);
		when(this.secondReference.isPrimary()).thenReturn(false);
		when(this.thirdReference.isPrimary()).thenReturn(false);
		when(this.firstReference.referenceType()).thenReturn((Class) this.getClass());
		when(this.secondReference.referenceType()).thenReturn((Class) this.getClass());
		when(this.thirdReference.referenceType()).thenReturn((Class) String.class);
		this.toTest.addReferences(setup, this.firstReferences);
		this.toTest.addReferences(setup, this.secondReferences);
		this.toTest.addReferences(setup, this.thirdReferences);
		this.toTest.setPrimaryEntityType(this.getClass());
		//when
		final List<EntityReference> references = this.toTest.fetchNonPrimaryReferences(setup);
		//then
		assertFalse(references.contains(this.firstReference));
		assertTrue(references.contains(this.secondReference));
		assertTrue(references.contains(this.thirdReference));
	}

	@Test
	public void testThatFetchDataSetForTestPhaseWorksForSetup() throws Exception {
		//when
		final IDataSet setupDataSet = this.toTest.fetchDataSet(TestPhase.Setup);
		//then
		assertNotNull(setupDataSet);
		assertEquals(this.setupDataSet, setupDataSet);
	}

	@Test
	public void testThatFetchDataSetForTestPhaseWorksForVerification() throws Exception {
		//when
		final IDataSet verificationDataSet = this.toTest.fetchDataSet(TestPhase.Verification);
		//then
		assertNotNull(verificationDataSet);
		assertEquals(this.verificationDataSet, verificationDataSet);
	}

	@Test
	public void testThatFetchDataSetForTestPhaseWorksForTearDown() throws Exception {
		//when
		final IDataSet tearDownDataSet = this.toTest.fetchDataSet(TestPhase.TearDown);
		//then
		assertNotNull(tearDownDataSet);
		assertEquals(this.tearDownDataSet, tearDownDataSet);
	}

	@Test(expected = RuntimeException.class)
	public void testThatFetchDataSetForTestPhaseWorksThrowsException() throws Exception {
		//when
		this.toTest.fetchDataSet(TestPhase.Running);
	}

	@Test
	public void testThatPrimaryTypeWorks() {
		//given
		this.toTest.setPrimaryEntityType(this.getClass());
		//then
		assertEquals(this.getClass(), this.toTest.primaryEntityType());
	}

	@Test
	public void testThatNumberOfPrimaryEntitiesWorks() {
		//given
		final int number = 2;
		this.toTest.setNumberOfPrimaryEntities(number);
		//when
		final int numberOfPrimaryEntities = this.toTest.numberOfPrimaryEntities();
		//then
		assertEquals(new Integer(number), (Integer) numberOfPrimaryEntities);
	}

	@SuppressWarnings("serial")
	private Set<EntityReference> prepareEntityReferences(final EntityReference reference) {
		return new TreeSet<EntityReference>(this.comparator) {{
			this.add(reference);
		}};
	}
}
