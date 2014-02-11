package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.Test;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;

public class EntityRelationshipCollectorTest {

	public static class ManyToOneTestEntity {
		@ManyToOne
		private OneToManyTestEntity oneToManyTestEntity;
	}

	public static class OneToManyTestEntity {
		@OneToMany(mappedBy="testEntityTwo")
		private List<ManyToOneTestEntity> manyToOneTestEntities;
	}

	public static class OneToOneTestEntityOwning {
		@OneToOne
		private OneToOneTestEntityRelating oneToOneTestEntityRelating;
	}

	public static class OneToOneTestEntityRelating {
		@OneToOne(mappedBy="oneToOneTestEntityRelating")
		private OneToOneTestEntityOwning oneToOneTestEntityOwning;
	}

	public static class ManyToManyTestEntityOwning {
		@ManyToMany
		private List<ManyToManyTestEntityRelating> manyToManyTestEntityRelatings;
	}

	public static class ManyToManyTestEntityRelating {
		@ManyToMany(mappedBy="manyToManyTestEntityRelating")
		private List<ManyToManyTestEntityOwning> manyToManyTestEntityOwnings;
	}

	@Test
	public void testThatManyToOneRelationshipIsCollected() throws Exception {
		//when
		final ManyToOneTestEntity collected = PersistenceTestContext.newInstance().entityRelationshipCollector().collect(ManyToOneTestEntity.class);
		//then
		assertNotNull(collected);
		assertNotNull(collected.oneToManyTestEntity);
		assertEquals(OneToManyTestEntity.class, collected.oneToManyTestEntity.getClass());
		assertEquals(new Integer(1), (Integer) PersistenceTestContext.instance().entityRelationshipPool().size());
	}

	@Test
	public void testThatOneToManyRelationshipIsCollected() throws Exception {
		//when
		final OneToManyTestEntity collected = PersistenceTestContext.newInstance().entityRelationshipCollector().collect(OneToManyTestEntity.class);
		//then
		assertNotNull(collected);
		assertNotNull(collected.manyToOneTestEntities);
		assertEquals(new Integer(1), (Integer) collected.manyToOneTestEntities.size());
		assertEquals(ManyToOneTestEntity.class, collected.manyToOneTestEntities.get(0).getClass());
		assertEquals(new Integer(1), (Integer) PersistenceTestContext.instance().entityRelationshipPool().size());
	}

	@Test
	public void testThatManyToManyRelationshipIsCollectedFromOwning() throws Exception {
		//when
		final ManyToManyTestEntityOwning collected = PersistenceTestContext.newInstance().entityRelationshipCollector().collect(ManyToManyTestEntityOwning.class);
		//then
		assertNotNull(collected);
		assertNotNull(collected.manyToManyTestEntityRelatings);
		assertEquals(new Integer(1), (Integer) collected.manyToManyTestEntityRelatings.size());
		assertEquals(ManyToManyTestEntityRelating.class, collected.manyToManyTestEntityRelatings.get(0).getClass());
		assertEquals(new Integer(1), (Integer) PersistenceTestContext.instance().entityRelationshipPool().size());
	}

	@Test
	public void testThatManyToManyRelationshipIsCollectedFromRelating() throws Exception {
		//when
		final ManyToManyTestEntityRelating collected = PersistenceTestContext.newInstance().entityRelationshipCollector().collect(ManyToManyTestEntityRelating.class);
		//then
		assertNotNull(collected);
		assertNotNull(collected.manyToManyTestEntityOwnings);
		assertEquals(new Integer(1), (Integer) collected.manyToManyTestEntityOwnings.size());
		assertEquals(ManyToManyTestEntityOwning.class, collected.manyToManyTestEntityOwnings.get(0).getClass());
		assertEquals(new Integer(1), (Integer) PersistenceTestContext.instance().entityRelationshipPool().size());
	}

	@Test
	public void testThatOneToOneRelationshipIsCollectedFromOwning() throws Exception {
		//when
		final OneToOneTestEntityOwning collected = PersistenceTestContext.newInstance().entityRelationshipCollector().collect(OneToOneTestEntityOwning.class);
		//then
		assertNotNull(collected);
		assertNotNull(collected.oneToOneTestEntityRelating);
		assertEquals(OneToOneTestEntityRelating.class, collected.oneToOneTestEntityRelating.getClass());
		assertEquals(new Integer(1), (Integer) PersistenceTestContext.instance().entityRelationshipPool().size());
	}

	@Test
	public void testThatOneToOneRelationshipIsCollectedFromRelating() throws Exception {
		//when
		final OneToOneTestEntityRelating collected = PersistenceTestContext.newInstance().entityRelationshipCollector().collect(OneToOneTestEntityRelating.class);
		//then
		assertNotNull(collected);
		assertNotNull(collected.oneToOneTestEntityOwning);
		assertEquals(OneToOneTestEntityOwning.class, collected.oneToOneTestEntityOwning.getClass());
		assertEquals(new Integer(1), (Integer) PersistenceTestContext.instance().entityRelationshipPool().size());
	}
}
