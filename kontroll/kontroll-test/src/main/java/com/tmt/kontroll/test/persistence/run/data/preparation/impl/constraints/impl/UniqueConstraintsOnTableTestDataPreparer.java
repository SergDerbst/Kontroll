package com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.updateField;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveFieldByColumnName;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveFieldValue;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.retrieveUniqueConstraintsOnTable;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.updateEntity;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.UniqueConstraintOnTableAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.ConstraintsTestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * Test data preparer for tests of {@link TestStrategy#UniqueConstraintsOnTable}.
 * </p>
 * Unique constraints on column tests assume that at least one primary entity exists in the database that has
 * unique constraints defined for its table. It will then try to update these with properties that would violate
 * the unique constraints. It then tests if the proper error is being thrown. Therefore, the references list
 * for {@link TestPhase#Running} needs to contain these violating entities.
 * </p>
 * For more information on test data preparation, see {@link TestDataPreparer}.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class UniqueConstraintsOnTableTestDataPreparer extends ConstraintsTestDataPreparer {

	private static class InstanceHolder {
		public static UniqueConstraintsOnTableTestDataPreparer instance;
	}

	public static UniqueConstraintsOnTableTestDataPreparer newInstance() {
		InstanceHolder.instance = new UniqueConstraintsOnTableTestDataPreparer();
		return InstanceHolder.instance;
	}

	private UniqueConstraintsOnTableTestDataPreparer() {}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.UniqueConstraintsOnTable == config.testStrategy();
	}

	@Override
	protected void handleConstraintEntity(final Object entity, final List<EntityReference> violatingReferences) throws Exception {
		for (final UniqueConstraint constraint : retrieveUniqueConstraintsOnTable(entity)) {
			final Object violatingEntity = updateEntity(entity.getClass().newInstance(), entity);
			for (final String columnName : constraint.columnNames()) {
				final Field field = retrieveFieldByColumnName(entity, columnName);
				updateField(violatingEntity, retrieveFieldValue(entity, field), field);
			}
			violatingReferences.add(new ConstraintReference(violatingEntity, new UniqueConstraintOnTableAssertion(constraint)));
		}
	}
}
