package com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints;

import java.util.Set;

import org.mockito.ArgumentMatcher;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparerTest;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

public abstract class ConstraintsTestDataPreparerTest extends TestDataPreparerTest {

	private static ConstraintsTestDataPreparerTest matchInstance;

	protected ConstraintsTestDataPreparerTest(final TestDataPreparer toTest,
	                                          final TestStrategy testStrategy) {
		super(toTest, testStrategy);
		matchInstance = this;
	}

	protected abstract boolean matchEntityReference(final EntityReference reference);

	protected abstract boolean matchConstraintReference(final ConstraintReference reference);

	public static class ConstraintReferencesSetMatcher extends ArgumentMatcher<Set<EntityReference>> {
		@Override
		@SuppressWarnings("unchecked")
		public boolean matches(final Object argument) {
			if (Set.class.isAssignableFrom(argument.getClass())) {
				final Set<EntityReference> references = (Set<EntityReference>) argument;
				for (final EntityReference reference : references) {
					if (reference instanceof ConstraintReference) {
						if (!matchInstance.matchConstraintReference((ConstraintReference) reference)) {
							return false;
						}
					} else {
						if (!matchInstance.matchEntityReference(reference)) {
							return false;
						}
					}
				}
			}
			return true;
		}
	}
}