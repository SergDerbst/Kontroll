package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import static com.tmt.kontroll.persistence.utils.EntityHelper.nullifyField;
import static com.tmt.kontroll.persistence.utils.EntityHelper.retrieveFieldsWithNullableConstraint;
import static com.tmt.kontroll.persistence.utils.EntityHelper.updateEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.NullableConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

public class NullableConstraintTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static NullableConstraintTestDataPreparer instance;
	}

	public static NullableConstraintTestDataPreparer newInstance() {
		InstanceHolder.instance = new NullableConstraintTestDataPreparer();
		return InstanceHolder.instance;
	}

	private NullableConstraintTestDataPreparer() {}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.NullableConstraint == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForRunning(final PersistenceTestConfig config,
	                                                  final List<Object> entities) throws Exception {
		final List<EntityReference> violatingReferences = new ArrayList<>();
		for (final Object entity : entities) {
			for (final Field field : retrieveFieldsWithNullableConstraint(entity)) {
				final Object violatingEntity = updateEntity(entity.getClass().newInstance(), entity);
				nullifyField(violatingEntity, field);
				violatingReferences.add(new ConstraintReference(violatingEntity, new NullableConstraintAssertion(field.getAnnotation(Column.class))));
			}
		}
		super.testDataHolder().addReferences(TestPhase.Running, violatingReferences);
	}
}
