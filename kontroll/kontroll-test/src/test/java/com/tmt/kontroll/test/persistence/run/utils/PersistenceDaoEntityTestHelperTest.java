package com.tmt.kontroll.test.persistence.run.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tmt.kontroll.test.persistence.run.utils.exceptions.EntityClassNotFoundException;



public class PersistenceDaoEntityTestHelperTest {

	private static final String Entity = "Entity";

	public static class EntityDaoServiceTest {}
	public static class EntityServiceTest {}
	public static class EntityFirstDaoTest {
		public static final String entityClassName = Entity;
	}
	public static class EntitySecondDaoTest {
		public static final String ENTITY_CLASS_NAME = Entity;
	}
	public static class ThrowsExceptionClassTest {}
	public static class ThrowsExceptionFieldTest {}

	@Test
	public void testThatRetrieveEntityClassNameWorksWithDaoServiceTestSuffix() {
		//when
		final String entityClassName = PersistenceDaoEntityTestHelper.retrieveEntityClassName(EntityDaoServiceTest.class);
		//then
		assertEquals("com.tmt.kontroll.test.persistence.run.utils.PersistenceDaoEntityTestHelperTest$" + Entity, entityClassName);
	}

	@Test
	public void testThatRetrieveEntityClassNameWorksWithServiceTestSuffix() {
		//when
		final String entityClassName = PersistenceDaoEntityTestHelper.retrieveEntityClassName(EntityServiceTest.class);
		//then
		assertEquals("com.tmt.kontroll.test.persistence.run.utils.PersistenceDaoEntityTestHelperTest$" + Entity, entityClassName);
	}

	@Test
	public void testThatRetrieveEntityClassNameWorksWithNonJavaStandardConstant() {
		//when
		final String entityClassName = PersistenceDaoEntityTestHelper.retrieveEntityClassName(EntityFirstDaoTest.class);
		//then
		assertEquals(Entity, entityClassName);
	}

	@Test
	public void testThatRetrieveEntityClassNameWorksWithJavaStandardConstant() {
		//when
		final String entityClassName = PersistenceDaoEntityTestHelper.retrieveEntityClassName(EntitySecondDaoTest.class);
		//then
		assertEquals(Entity, entityClassName);
	}

	@Test(expected = EntityClassNotFoundException.class)
	public void testThatExceptionIsThrownOnUnknownSuffix() {
		//when
		PersistenceDaoEntityTestHelper.retrieveEntityClassName(ThrowsExceptionClassTest.class);
	}
}
