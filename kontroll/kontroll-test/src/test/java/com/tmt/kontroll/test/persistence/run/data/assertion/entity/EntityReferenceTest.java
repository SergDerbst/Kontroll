package com.tmt.kontroll.test.persistence.run.data.assertion.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;

/**
 * <b><i>Note:</i></b>
 * </br>
 * If you encounter a <code>java.lang.VerifyError</code> because of some inconsistent stackmap frames,
 * please make sure to follow these <a href="http://blog.triona.de/development/jee/how-to-use-powermock-with-java-7.html">instructions</a>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PersistenceTestContext.class})
public class EntityReferenceTest {

	public static class TestEntity {
		public String stringField = "string";
		public Integer integerField = 0;
	}

	@Mock
	private PersistenceTestContext persistenceTestContext;
	@Mock
	private TestDataHolder testDataHolder;
	@Mock
	private Set<EntityReference> allReferences;

	private TestEntity testEntity;
	private EntityReference toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(PersistenceTestContext.class);
		when(PersistenceTestContext.instance()).thenReturn(this.persistenceTestContext);
		when(this.persistenceTestContext.testDataHolder()).thenReturn(this.testDataHolder);
		when(this.testDataHolder.allReferences()).thenReturn(this.allReferences);
		this.testEntity = new TestEntity();
		this.toTest = new EntityReference(this.testEntity, false, false);
	}

	@Test
	public void testThatEntityReferenceIsConstructedProperlyWithoutAddingOtToThePersistenceTestContext() {
		//then
		assertNotNull(this.toTest.entity());
		assertEquals(TestEntity.class, this.toTest.referenceType());
		assertEquals(new Integer(2), (Integer) this.toTest.referenceEntrySet().size());
		assertEquals("string", this.toTest.referenceValue("stringField"));
		assertEquals(new Integer(0), this.toTest.referenceValue("integerField"));
		assertFalse(this.toTest.isPrimary());
		verify(this.persistenceTestContext, never()).testDataHolder();
		verify(this.testDataHolder, never()).allReferences();
		verify(this.allReferences, never()).add(any(EntityReference.class));
	}

	@Test
	public void testThatEntityReferenceIsConstructedProperlyWithAddingItToThePersistenceTestContext() {
		//when
		this.toTest = new EntityReference(this.testEntity, true, true);
		//then
		assertNotNull(this.toTest.entity());
		assertEquals(TestEntity.class, this.toTest.referenceType());
		assertEquals(new Integer(2), (Integer) this.toTest.referenceEntrySet().size());
		assertEquals("string", this.toTest.referenceValue("stringField"));
		assertEquals(new Integer(0), this.toTest.referenceValue("integerField"));
		assertTrue(this.toTest.isPrimary());
		verify(this.persistenceTestContext).testDataHolder();
		verify(this.testDataHolder).allReferences();
		verify(this.allReferences).add(any(EntityReference.class));
	}

	@Test
	public void testThatReferenceValueMapCanBeUpdatedProperly() {
		//given
		this.testEntity.stringField = "newString";
		//when
		this.toTest.updateReferenceValueMap();
		//then
		assertEquals(new Integer(2), (Integer) this.toTest.referenceEntrySet().size());
		assertEquals("newString", this.toTest.referenceValue("stringField"));
		assertEquals(new Integer(0), this.toTest.referenceValue("integerField"));
	}
}
