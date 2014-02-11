package com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.updateField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveFieldValue;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveFieldsWithUniqueConstraint;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.updateEntity;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.UniqueConstraintOnColumnAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.ConstraintsTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#UniqueConstraintsOnColumn}.
 * </p>
 * Unique constraints on column tests assume that at least one primary entity exists in the database that has
 * unique constraints set on their fields. It will then try to update these with properties that would violate
 * the unique constraints. It then tests if the proper error is being thrown. Therefore, the references list
 * for {@link TestPhase#Running} needs to contain these violating entities.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class UniqueConstraintsOnColumnTestDataPreparer extends ConstraintsTestDataPreparer {

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
	protected void handleConstraintEntity(final EntityReference reference, final List<EntityReference> violatingReferences) throws Exception {
		for (final Field field : retrieveFieldsWithUniqueConstraint(reference.getEntity())) {
			final Object violatingEntity = updateEntity(reference.getReferenceType().newInstance(), reference.getEntity());
			updateField(violatingEntity, retrieveFieldValue(reference.getEntity(), field), field);
			violatingReferences.add(new ConstraintReference(violatingEntity, new UniqueConstraintOnColumnAssertion(field.getAnnotation(Column.class))));
		}
	}
}
