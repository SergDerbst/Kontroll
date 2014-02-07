package com.tmt.kontroll.test.persistence.run.data.preparation;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintAsserter;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReferenceAsserter;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityUpdateProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;

public class TestPreparationContext {

	private static class InstanceHolder {
		public static TestPreparationContext instance;
	}

	public static TestPreparationContext newInstance() {
		InstanceHolder.instance = new TestPreparationContext();
		return InstanceHolder.instance;
	}

	public static TestPreparationContext instance() {
		return InstanceHolder.instance;
	}

	private final ConstraintAsserter constraintAsserter;
	private final EntityInstanceProvider entityInstanceProvider;
	private final EntityUpdateProvider entityUpdateProvider;
	private final EntityReferenceAsserter referenceAsserter;
	private final TestDataComplianceAssurer tableComplianceAssurer;
	private final TestDataHolder testDataHolder;
	private final TestDataPreparationHandler testDataPreparationHandler;
	private final TestDataSetBuilder testDataSetBuilder;
	private final ValueProvisionHandler valueProvisionHandler;

	private TestPreparationContext() {
		this.constraintAsserter = ConstraintAsserter.newInstance();
		this.entityInstanceProvider = EntityInstanceProvider.newInstance();
		this.entityUpdateProvider = EntityUpdateProvider.newInstance();
		this.referenceAsserter = EntityReferenceAsserter.newInstance();
		this.tableComplianceAssurer = TestDataComplianceAssurer.newInstance();
		this.testDataHolder = TestDataHolder.newInstance();
		this.testDataPreparationHandler = TestDataPreparationHandler.newInstance();
		this.testDataSetBuilder = TestDataSetBuilder.newInstance();
		this.valueProvisionHandler = ValueProvisionHandler.newInstance();
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

	public ValueProvisionHandler valueProvisionHandler() {
		return this.valueProvisionHandler;
	}

	public ConstraintAsserter constraintAsserter() {
		return this.constraintAsserter;
	}
}
