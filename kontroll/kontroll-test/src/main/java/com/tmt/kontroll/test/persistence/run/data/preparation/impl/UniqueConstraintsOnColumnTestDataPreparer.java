package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import static com.tmt.kontroll.persistence.utils.EntityHelper.retrieveFieldValue;
import static com.tmt.kontroll.persistence.utils.EntityHelper.retrieveFieldsWithUniqueConstraint;
import static com.tmt.kontroll.persistence.utils.EntityHelper.updateEntity;
import static com.tmt.kontroll.persistence.utils.EntityHelper.updateField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.UniqueConstraintOnColumnAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

public class UniqueConstraintsOnColumnTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static UniqueConstraintsOnColumnTestDataPreparer instance;
	}

	public static UniqueConstraintsOnColumnTestDataPreparer newInstance() {
		InstanceHolder.instance = new UniqueConstraintsOnColumnTestDataPreparer();
		return InstanceHolder.instance;
	}

	private UniqueConstraintsOnColumnTestDataPreparer() {}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.UniqueConstraintsOnColumn == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForRunning(final PersistenceTestConfig config,
	                                                  final List<Object> entities) throws Exception {
		final List<EntityReference> violatingReferences = new ArrayList<>();
		for (final Object entity : entities) {
			for (final Field field : retrieveFieldsWithUniqueConstraint(entity)) {
				final Object violatingEntity = updateEntity(entity.getClass().newInstance(), entity);
				updateField(violatingEntity, retrieveFieldValue(entity, field), field);
				violatingReferences.add(new ConstraintReference(violatingEntity, new UniqueConstraintOnColumnAssertion(field.getAnnotation(Column.class))));
			}
		}
		super.testDataHolder().addReferences(TestPhase.Running, violatingReferences);
	}
}
