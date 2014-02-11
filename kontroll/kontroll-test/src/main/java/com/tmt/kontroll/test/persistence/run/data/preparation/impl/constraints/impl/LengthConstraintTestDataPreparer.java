package com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.updateField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveFieldsWithLengthConstraint;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.updateEntity;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang3.RandomStringUtils;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.LengthConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.ConstraintsTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#LengthConstraint}.
 * </p>
 * Length constraints tests assume that at least one primary entity exists in the database. It will then
 * try to update these with properties that would violate length constraints. It then tests if the proper
 * error is being thrown. Therefore, the references list for {@link TestPhase#Running} needs to contain
 * these violating entities.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class LengthConstraintTestDataPreparer extends ConstraintsTestDataPreparer {

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
	protected void handleConstraintEntity(final EntityReference reference, final List<EntityReference> violatingReferences) throws Exception {
		for (final Field field : retrieveFieldsWithLengthConstraint(reference.getEntity())) {
			final Object violatingEntity = updateEntity(reference.getReferenceType().newInstance(), reference.getEntity());
			final Column column = field.getAnnotation(Column.class);
			updateField(violatingEntity, RandomStringUtils.random(column.length() + 1), field);
			violatingReferences.add(new ConstraintReference(violatingEntity, new LengthConstraintAssertion(column)));
		}
	}
}
