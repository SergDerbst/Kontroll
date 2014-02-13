package com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.impl;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.ConstraintReference;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.impl.constraints.ConstraintsTestDataPreparerTest;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestPhase;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * <b><i>Note:</i></b>
 * </br>
 * If you encounter a <code>java.lang.VerifyError</code> babbling about some inconsistent stackmap frames,
 * please make sure to follow these <a href="http://blog.triona.de/development/jee/how-to-use-powermock-with-java-7.html">instructions</a>.
 * </p>
 * 
 * @author Serg Derbst
 *
 */
public class NullableConstraintTestDataPreparerTest extends ConstraintsTestDataPreparerTest {

	public NullableConstraintTestDataPreparerTest() {
		super(NullableConstraintTestDataPreparer.newInstance(), TestStrategy.NullableConstraint);
	}

	@Override
	protected void verifyReferencesAfterTest() {
		verify(super.testDataHolder).addReferences(TestPhase.Setup, super.references);
		verify(super.testDataHolder).addReferences(eq(TestPhase.Running), argThat(new ConstraintReferencesSetMatcher()));
		verify(super.testDataHolder).addReferences(TestPhase.Verification, super.references);
		verify(super.testDataHolder).addReferences(TestPhase.TearDown, new TreeSet<EntityReference>());
	}

	@Override
	protected boolean matchEntityReference(final EntityReference reference) {
		return false;
	}

	@Override
	protected boolean matchConstraintReference(final ConstraintReference reference) {
		if (reference.isPrimary() && reference.getReferenceType().equals(Dummy.class)) {
			final String value = ((Dummy )reference.getEntity()).nullConstraintField;
			return value == null;
		}
		return false;
	}
}
