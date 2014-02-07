package com.tmt.kontroll.test.persistence.run.data.preparation.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

public class UpdateTestDataPreparer extends TestDataPreparer {

	private static class InstanceHolder {
		public static UpdateTestDataPreparer instance;
	}

	public static UpdateTestDataPreparer newInstance() {
		InstanceHolder.instance = new UpdateTestDataPreparer();
		return InstanceHolder.instance;
	}

	@Override
	protected boolean isResponsible(final PersistenceTestConfig config) {
		return TestStrategy.Update == config.testStrategy();
	}

	@Override
	protected void prepareReferenceEntitiesForRunning(final PersistenceTestConfig config,
	                                                  final List<Object> entities) {
		this.prepareUpdatedReferenceEntities(entities, TestPhase.Running);
	}

	@Override
	protected void prepareReferenceEntitiesForVerification(final PersistenceTestConfig config,
	                                                       final List<Object> entities) {
		this.prepareUpdatedReferenceEntities(entities, TestPhase.Verification);
	}

	private void prepareUpdatedReferenceEntities(final List<Object> entities,
	                                             final TestPhase testPhase) {
		final List<Object> updatedEntities = new ArrayList<>();
		for (final Object entity : entities) {
			updatedEntities.add(super.entityUpdateProvider().provideNewUpdated(entity));
		}
		super.prepareReferenceEntitiesForTestPhase(testPhase, updatedEntities);
	}
}
