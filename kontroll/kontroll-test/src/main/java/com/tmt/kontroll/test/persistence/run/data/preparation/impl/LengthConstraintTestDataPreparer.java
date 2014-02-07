package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import static com.tmt.kontroll.persistence.utils.EntityHelper.retrieveFieldsWithLengthConstraint;
import static com.tmt.kontroll.persistence.utils.EntityHelper.updateEntity;
import static com.tmt.kontroll.persistence.utils.EntityHelper.updateField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang3.RandomStringUtils;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.LengthConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

public class LengthConstraintTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static LengthConstraintTestDataPreparer instance;
	}

	public static LengthConstraintTestDataPreparer newInstance() {
		InstanceHolder.instance = new LengthConstraintTestDataPreparer();
		return InstanceHolder.instance;
	}

	private LengthConstraintTestDataPreparer() {}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.LengthConstraint == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForRunning(final PersistenceTestConfig config,
	                                                  final List<Object> entities) throws Exception {
		final List<EntityReference> violatingReferences = new ArrayList<>();
		for (final Object entity : entities) {
			for (final Field field : retrieveFieldsWithLengthConstraint(entity)) {
				final Object violatingEntity = updateEntity(entity.getClass().newInstance(), entity);
				final Column column = field.getAnnotation(Column.class);
				updateField(violatingEntity, RandomStringUtils.random(column.length() + 1), field);
				violatingReferences.add(new ConstraintReference(violatingEntity, new LengthConstraintAssertion(column)));
			}
		}
		super.testDataHolder().addReferences(TestPhase.Running, violatingReferences);
	}
}
