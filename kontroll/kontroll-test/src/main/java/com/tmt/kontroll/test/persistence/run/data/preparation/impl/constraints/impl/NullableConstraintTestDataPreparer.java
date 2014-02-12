package com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.nullifyField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveFieldsWithNullableConstraint;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.updateEntity;

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Column;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.NullableConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.ConstraintsTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#NullableConstraint}.
 * </p>
 * Nullable constraints tests assume that at least one primary entity exists in the database. It will then
 * try to update these with properties that would violate nullable constraints. It then tests if the proper
 * error is being thrown. Therefore, the references list for {@link TestPhase#Running} needs to contain
 * these violating entities.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class NullableConstraintTestDataPreparer extends ConstraintsTestDataPreparer {

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
	protected void handleConstraintEntity(final EntityReference reference, final Set<EntityReference> violatingReferences) throws Exception {
		for (final Field field : retrieveFieldsWithNullableConstraint(reference.getEntity())) {
			final Object violatingEntity = updateEntity(reference.getReferenceType().newInstance(), reference.getEntity());
			nullifyField(violatingEntity, field);
			violatingReferences.add(new ConstraintReference(violatingEntity, new NullableConstraintAssertion(field.getAnnotation(Column.class))));
		}
	}
}
