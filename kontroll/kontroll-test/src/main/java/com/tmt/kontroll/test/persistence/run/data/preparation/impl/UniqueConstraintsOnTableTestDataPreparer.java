package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import static com.tmt.kontroll.persistence.utils.EntityHelper.retrieveFieldByColumnName;
import static com.tmt.kontroll.persistence.utils.EntityHelper.retrieveFieldValue;
import static com.tmt.kontroll.persistence.utils.EntityHelper.retrieveUniqueConstraintsOnTable;
import static com.tmt.kontroll.persistence.utils.EntityHelper.updateEntity;
import static com.tmt.kontroll.persistence.utils.EntityHelper.updateField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.impl.UniqueConstraintOnTableAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

/**
 * Test preparer to test {@link UniqueConstraint} violations.
 * 
 * @author Serg Derbst
 *
 */
public class UniqueConstraintsOnTableTestDataPreparer extends TestDataPreparer {

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
	protected void prepareReferenceEntitiesForRunning(final PersistenceTestConfig config,
	                                                  final List<Object> entities) throws Exception {
		final List<EntityReference> violatingReferences = new ArrayList<>();
		for (final Object entity : entities) {
			for (final UniqueConstraint constraint : retrieveUniqueConstraintsOnTable(entity)) {
				final Object violatingEntity = updateEntity(entity.getClass().newInstance(), entity);
				for (final String columnName : constraint.columnNames()) {
					final Field field = retrieveFieldByColumnName(entity, columnName);
					updateField(violatingEntity, retrieveFieldValue(entity, field), field);
				}
				violatingReferences.add(new ConstraintReference(violatingEntity, new UniqueConstraintOnTableAssertion(constraint)));
			}
		}
		super.testDataHolder().addReferences(TestPhase.Running, violatingReferences);
	}
}
