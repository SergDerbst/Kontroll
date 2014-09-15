package com.tmt.kontroll.test.persistence.run.data.assertion.entity;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;

public class EntityReferenceAsserterTest {

	public static class TestEntity {
		public String stringField;
		public Integer integerField;
	}

	@Mock
	private PersistenceTestConfig config;

	private EntityReferenceAsserter toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		when(this.config.ignoredFields()).thenReturn(new String[]{"integerField"});
		this.toTest = EntityReferenceAsserter.newInstance();
	}

	@Test
	public void testThatAssertionSucceeds() throws Exception {
		//given
		final TestEntity expectedEntity = new TestEntity();
		expectedEntity.stringField = "s";
		expectedEntity.integerField = 0;
		final TestEntity actualEntity = new TestEntity();
		actualEntity.stringField = "s";
		actualEntity.integerField = 0;
		final List<EntityReference> expectedList = new ArrayList<>();
		expectedList.add(new EntityReference(expectedEntity, false, false));
		final List<Object> actualList = new ArrayList<>();
		actualList.add(actualEntity);
		//when
		this.toTest.assertReferences(expectedList, actualList);
		//then nothing should fail
	}

	@Test
	public void testThatAssertionFailsWithNumberOfEntities() throws Exception {
		//given
		final TestEntity expectedEntity = new TestEntity();
		final TestEntity firstActualEntity = new TestEntity();
		final TestEntity secondActualEntity = new TestEntity();
		final List<EntityReference> expectedList = new ArrayList<>();
		expectedList.add(new EntityReference(expectedEntity, false, false));
		final List<Object> actualList = new ArrayList<>();
		actualList.add(firstActualEntity);
		actualList.add(secondActualEntity);
		//when
		try {
			this.toTest.assertReferences(expectedList, actualList);
		} catch (final AssertionError e) {
			assertTrue(e.getMessage().contains(EntityReferenceAssertion.NumberOfEntities.message()));
		}
	}

	@Test
	public void testThatAssertionFailsWithEntityType() throws Exception {
		//given
		final TestEntity expectedEntity = new TestEntity();
		final List<EntityReference> expectedList = new ArrayList<>();
		expectedList.add(new EntityReference(expectedEntity, false, false));
		final List<Object> actualList = new ArrayList<>();
		actualList.add(this);
		//when
		try {
			this.toTest.assertReferences(expectedList, actualList);
		} catch (final AssertionError e) {
			assertTrue(e.getMessage().contains(EntityReferenceAssertion.EntityType.message()));
		}
	}

	@Test
	public void testThatAssertionFailsWithFieldValue() throws Exception {
		//given
		final TestEntity expectedEntity = new TestEntity();
		expectedEntity.stringField = "s";
		expectedEntity.integerField = 0;
		final TestEntity actualEntity = new TestEntity();
		actualEntity.stringField = "s";
		actualEntity.integerField = 1;
		final List<EntityReference> expectedList = new ArrayList<>();
		expectedList.add(new EntityReference(expectedEntity, false, false));
		final List<Object> actualList = new ArrayList<>();
		actualList.add(actualEntity);
		//when
		try {
			this.toTest.assertReferences(expectedList, actualList);
		} catch (final AssertionError e) {
			assertTrue(e.getMessage().contains(EntityReferenceAssertion.FieldValueOfEntity.message()));
		}
	}

	@Test
	public void testThatAssertionWorksWithIgnoredFields() throws Exception {
		//given
		this.toTest.configureForIgnoredFields(this.config);
		final TestEntity expectedEntity = new TestEntity();
		expectedEntity.stringField = null;
		expectedEntity.integerField = 0;
		final TestEntity actualEntity = new TestEntity();
		actualEntity.stringField = null;
		actualEntity.integerField = 1;
		final List<EntityReference> expectedList = new ArrayList<>();
		expectedList.add(new EntityReference(expectedEntity, false, false));
		final List<Object> actualList = new ArrayList<>();
		actualList.add(actualEntity);
		//when
		this.toTest.assertReferences(expectedList, actualList);
		//then nothing should fail
	}
}
