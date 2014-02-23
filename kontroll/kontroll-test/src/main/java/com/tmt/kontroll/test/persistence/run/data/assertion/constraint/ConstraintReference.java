package com.tmt.kontroll.test.persistence.run.data.assertion.constraint;

import com.tmt.kontroll.test.persistence.run.data.assertion.constraint.assertion.ConstraintAssertion;
import com.tmt.kontroll.test.persistence.run.data.assertion.entity.EntityReference;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestDataPreparer;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * A constraint reference is a special form of {@link EntityReference} used for testing
 * constraints of entities. Their referenced entities have values that will cause a
 * constraint violation, when tried to be written to the database. They also contain a
 * {@link ConstraintAssertion} of the kind of constraint violation to be tested.
 * </p>
 * They are created by the {@link TestDataPreparer} dedicated to the respective
 * {@link TestStrategy} for testing the constraint violation.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class ConstraintReference extends EntityReference {

	private final ConstraintAssertion constraintAssertion;

	public ConstraintReference(final Object entity,
	                           final ConstraintAssertion constraintAssertion,
	                           final boolean isPrimary,
	                           final boolean addToPersistenceTestContext) {
		super(entity, isPrimary, addToPersistenceTestContext);
		this.constraintAssertion = constraintAssertion;
	}

	public ConstraintAssertion constraintAssertion() {
		return this.constraintAssertion;
	}
}
