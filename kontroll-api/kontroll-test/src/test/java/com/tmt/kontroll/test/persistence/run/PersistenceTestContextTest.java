package com.tmt.kontroll.test.persistence.run;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintAsserter;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.building.TestDataSetBuilder;
import com.tmt.kontroll.test.persistence.run.data.building.columns.TestDataSetColumnBuildingHandler;
import com.tmt.kontroll.test.persistence.run.data.building.compliance.TestDataComplianceAssurer;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparationHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityUpdateProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships.EntityRelationshipCollector;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships.EntityRelationshipPool;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class PersistenceTestContextTest {

	@Test
	public void testThatPersistenceTestContextIsSetup() throws Exception {
		//when
		PersistenceTestContext.newInstance();
		//then
		assertNotNull(PersistenceTestContext.instance().constraintAsserter());
		assertEquals(ConstraintAsserter.class, PersistenceTestContext.instance().constraintAsserter().getClass());
		assertNotNull(PersistenceTestContext.instance().entityInstanceProvider());
		assertEquals(EntityInstanceProvider.class, PersistenceTestContext.instance().entityInstanceProvider().getClass());
		assertNotNull(PersistenceTestContext.instance().entityRelationshipCollector());
		assertEquals(EntityRelationshipCollector.class, PersistenceTestContext.instance().entityRelationshipCollector().getClass());
		assertNotNull(PersistenceTestContext.instance().entityReferenceAsserter());
		assertEquals(EntityReferenceAsserter.class, PersistenceTestContext.instance().entityReferenceAsserter().getClass());
		assertNotNull(PersistenceTestContext.instance().entityRelationshipPool());
		assertEquals(EntityRelationshipPool.class, PersistenceTestContext.instance().entityRelationshipPool().getClass());
		assertNotNull(PersistenceTestContext.instance().entityUpdateProvider());
		assertEquals(EntityUpdateProvider.class, PersistenceTestContext.instance().entityUpdateProvider().getClass());
		assertNotNull(PersistenceTestContext.instance().testDataComplianceAssurer());
		assertEquals(TestDataComplianceAssurer.class, PersistenceTestContext.instance().testDataComplianceAssurer().getClass());
		assertNotNull(PersistenceTestContext.instance().testDataHolder());
		assertEquals(TestDataHolder.class, PersistenceTestContext.instance().testDataHolder().getClass());
		assertNotNull(PersistenceTestContext.instance().testDataPreparationHandler());
		assertEquals(TestDataPreparationHandler.class, PersistenceTestContext.instance().testDataPreparationHandler().getClass());
		assertNotNull(PersistenceTestContext.instance().testDataSetBuilder());
		assertEquals(TestDataSetBuilder.class, PersistenceTestContext.instance().testDataSetBuilder().getClass());
		assertNotNull(PersistenceTestContext.instance().testDataSetColumnBuildingHandler());
		assertEquals(TestDataSetColumnBuildingHandler.class, PersistenceTestContext.instance().testDataSetColumnBuildingHandler().getClass());
		assertNotNull(PersistenceTestContext.instance().valueProvisionHandler());
		assertEquals(ValueProvisionHandler.class, PersistenceTestContext.instance().valueProvisionHandler().getClass());
	}

}
