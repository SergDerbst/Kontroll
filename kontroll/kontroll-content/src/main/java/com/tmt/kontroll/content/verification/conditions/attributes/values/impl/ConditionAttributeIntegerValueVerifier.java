package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class ConditionAttributeIntegerValueVerifier extends ConditionAttributeValueVerifier<Integer> {

	private static class IntegerComparator implements Comparator<Integer> {
		@Override
		public int compare(final Integer o1, final Integer o2) {
			return o1.intValue() - o2.intValue();
		}
	}

	static final IntegerComparator comparator = new IntegerComparator();

	@Override
	protected boolean isResponsible(final Class<?> valueType) {
		return Integer.class.equals(valueType) || Integer.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Integer> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(final Integer expectedValue, final Integer actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.intValue() == actualValue.intValue();
		}
		return true;
	}
}
