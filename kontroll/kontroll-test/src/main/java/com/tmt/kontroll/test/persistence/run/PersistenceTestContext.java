package com.tmt.kontroll.test.persistence.run;

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

public class PersistenceTestContext {

	private static class InstanceHolder {
		public static PersistenceTestContext instance;
	}

	public static PersistenceTestContext newInstance() {
		InstanceHolder.instance = new PersistenceTestContext();
		return InstanceHolder.instance;
	}

	public static PersistenceTestContext instance() {
		return InstanceHolder.instance;
	}

	private final ConstraintAsserter constraintAsserter;
	private final EntityInstanceProvider entityInstanceProvider;
	private final EntityRelationshipCollector entityRelationshipCollector;
	private final EntityRelationshipPool entityRelationshipPool;
	private final EntityUpdateProvider entityUpdateProvider;
	private final EntityReferenceAsserter referenceAsserter;
	private final TestDataComplianceAssurer tableComplianceAssurer;
	private final TestDataHolder testDataHolder;
	private final TestDataPreparationHandler testDataPreparationHandler;
	private final TestDataSetBuilder testDataSetBuilder;
	private final TestDataSetColumnBuildingHandler testDataSetColumnBuildingHandler;
	private final ValueProvisionHandler valueProvisionHandler;

	private PersistenceTestContext() {
		this.constraintAsserter = ConstraintAsserter.newInstance();
		this.entityInstanceProvider = EntityInstanceProvider.newInstance();
		this.entityRelationshipCollector = EntityRelationshipCollector.newInstance();
		this.entityRelationshipPool = EntityRelationshipPool.newInstance();
		this.entityUpdateProvider = EntityUpdateProvider.newInstance();
		this.referenceAsserter = EntityReferenceAsserter.newInstance();
		this.tableComplianceAssurer = TestDataComplianceAssurer.newInstance();
		this.testDataHolder = TestDataHolder.newInstance();
		this.testDataPreparationHandler = TestDataPreparationHandler.newInstance();
		this.testDataSetBuilder = TestDataSetBuilder.newInstance();
		this.testDataSetColumnBuildingHandler = TestDataSetColumnBuildingHandler.instance();
		this.valueProvisionHandler = ValueProvisionHandler.newInstance();
	}

	public ConstraintAsserter constraintAsserter() {
		return this.constraintAsserter;
	}

	public EntityInstanceProvider entityInstanceProvider() {
		return this.entityInstanceProvider;
	}

	public EntityUpdateProvider entityUpdateProvider() {
		return this.entityUpdateProvider;
	}

	public EntityReferenceAsserter referenceAsserter() {
		return this.referenceAsserter;
	}

	public EntityRelationshipCollector entityRelationshipCollector() {
		return this.entityRelationshipCollector;
	}

	public EntityRelationshipPool entityRelationshipPool() {
		return this.entityRelationshipPool;
	}

	public TestDataComplianceAssurer testDataComplianceAssurer() {
		return this.tableComplianceAssurer;
	}

	public TestDataHolder testDataHolder() {
		return this.testDataHolder;
	}

	public TestDataPreparationHandler testDataPreparationHandler() {
		return this.testDataPreparationHandler;
	}

	public TestDataSetBuilder testDataSetBuilder() {
		return this.testDataSetBuilder;
	}

	public TestDataSetColumnBuildingHandler testDataSetColumnBuildingHandler() {
		return this.testDataSetColumnBuildingHandler;
	}

	public ValueProvisionHandler valueProvisionHandler() {
		return this.valueProvisionHandler;
	}
}
