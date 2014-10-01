package com.tmt.kontroll.content.verification.conditions.attributes.values;

import java.util.Comparator;

import com.tmt.kontroll.content.persistence.selections.BooleanOperator;

public abstract class ConditionAttributeValueVerifier<V> {

	private ConditionAttributeValueVerifier<?> nextVerifier;

	protected abstract boolean isResponsible(final Class<?> valueType);

	protected abstract Comparator<V> getComparator();

	protected abstract boolean isEqual(final V expectedValue, final V actualValue);

	protected boolean checkNullEquality(final V expectedValue, final V actualValue) {
		if (expectedValue == null) {
			return actualValue == null;
		}
		return false;
	}

	protected boolean doVerify(final V expectedValue, final V actualValue, final BooleanOperator operator) {
		switch (operator) {
			case IsEqual:
				return this.isEqual(expectedValue, actualValue);
			case IsNotEqual:
				return !this.isEqual(expectedValue, actualValue);
			case IsLargerAs:
				return this.getComparator().compare(expectedValue, actualValue) > 0;
			case IsSmallerAs:
				return this.getComparator().compare(expectedValue, actualValue) < 0;
			case IsLargerOrEqualAs:
				return this.getComparator().compare(expectedValue, actualValue) >= 0;
			case IsSmallerOrEqualAs:
				return this.getComparator().compare(expectedValue, actualValue) <= 0;
			default:
				throw new RuntimeException("Could not handle reference operator: " + operator.toString());
		}
	}

	protected ConditionAttributeValueVerifier<?> getNextVerifier() {
		return this.nextVerifier;
	}

	@SuppressWarnings("unchecked")
	public boolean verify(final Object expectedValue,
	                      final Object actualValue,
	                      final Class<?> valueType,
	                      final BooleanOperator operator) {
		if (this.isResponsible(valueType)) {
			return this.doVerify((V) expectedValue, (V) actualValue, operator);
		}
		if (this.getNextVerifier() == null) {
			throw new RuntimeException("No condition attribute verifier found for value type '" + valueType.getName() + "' and operator '" + operator.toString() + "'.");
		}
		return this.getNextVerifier().verify(expectedValue, actualValue, valueType, operator);
	}

	public void setNextVerifier(final ConditionAttributeValueVerifier<?> nextVerifier) {
		this.nextVerifier = nextVerifier;
	}
}