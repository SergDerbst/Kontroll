package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tmt.kontroll.content.persistence.selections.ReferenceOperator;
import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public abstract class ConditionAttributeValueVerifierTest<R, N> {

	@Mock
	private ConditionAttributeValueVerifier<N> nextVerifier;

	private final ConditionAttributeValueVerifier<R> toTest;
	private final Class<R> responsibleType;
	private final R responsibleValue;
	private final Class<N> notResponsibleType;
	private final N notResponsibleValue;

	protected ConditionAttributeValueVerifierTest(final ConditionAttributeValueVerifier<R> toTest,
	                                              final Class<R> responsibleType,
	                                              final R responsibleValue,
	                                              final Class<N> notResponsibleType,
	                                              final N notResponsibleValue) {
		this.toTest = toTest;
		this.responsibleType = responsibleType;
		this.notResponsibleType = notResponsibleType;
		this.notResponsibleValue = notResponsibleValue;
		this.responsibleValue = responsibleValue;
	}

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testThatNullEqualityIsHandledProperly() {
		assertTrue(this.toTest.verify(null, null, this.responsibleType, ReferenceOperator.IsEqual));
		assertFalse(this.toTest.verify(this.responsibleValue, null, this.responsibleType, ReferenceOperator.IsEqual));
		assertFalse(this.toTest.verify(null, this.responsibleValue, this.responsibleType, ReferenceOperator.IsEqual));
	}

	@Test
	public void testThatNextVerifierIsCalled() {
		//given
		this.toTest.setNextVerifier(this.nextVerifier);
		//when
		this.toTest.verify(this.notResponsibleValue, this.notResponsibleValue, this.notResponsibleType, ReferenceOperator.IsEqual);
		//then
		verify(this.nextVerifier).verify(this.notResponsibleValue, this.notResponsibleValue, this.notResponsibleType, ReferenceOperator.IsEqual);
	}

	@Test(expected = RuntimeException.class)
	public void testThatWhenNextVerifierIsNullRuntimeExceptionIsThrown() {
		//given
		this.toTest.setNextVerifier(null);
		//when
		this.toTest.verify(this.notResponsibleValue, this.notResponsibleValue, this.notResponsibleType, ReferenceOperator.IsEqual);
	}

	protected void testIsEqual(final Object expectedValue, final Object actualValue, final boolean reference) {
		assertEquals(reference, this.toTest.verify(expectedValue, actualValue, this.responsibleType, ReferenceOperator.IsEqual));
	}

	protected void testIsNotEqual(final Object expectedValue, final Object actualValue, final boolean reference) {
		assertEquals(reference, this.toTest.verify(expectedValue, actualValue, this.responsibleType, ReferenceOperator.IsNotEqual));
	}

	protected void testIsLargerAs(final Object expectedValue, final Object actualValue, final boolean reference) {
		assertEquals(reference, this.toTest.verify(expectedValue, actualValue, this.responsibleType, ReferenceOperator.IsLargerAs));
	}

	protected void testIsLargerOrEqualAs(final Object expectedValue, final Object actualValue, final boolean reference) {
		assertEquals(reference, this.toTest.verify(expectedValue, actualValue, this.responsibleType, ReferenceOperator.IsLargerOrEqualAs));
	}

	protected void testIsSmallerAs(final Object expectedValue, final Object actualValue, final boolean reference) {
		assertEquals(reference, this.toTest.verify(expectedValue, actualValue, this.responsibleType, ReferenceOperator.IsSmallerAs));
	}

	protected void testIsSmallerOrEqualAs(final Object expectedValue, final Object actualValue, final boolean reference) {
		assertEquals(reference, this.toTest.verify(expectedValue, actualValue, this.responsibleType, ReferenceOperator.IsSmallerOrEqualAs));
	}
}
