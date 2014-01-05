package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class ConditionAttributeDoubleValueVerifier extends ConditionAttributeValueVerifier<Double> {

	private static class DoubleComparator implements Comparator<Double> {
		@Override
		public int compare(final Double o1, final Double o2) {
			return (int) Math.ceil(o1.doubleValue() - o2.doubleValue());
		}
	}

	static final DoubleComparator comparator = new DoubleComparator();

	@Override
	protected boolean isResponsible(final Class<?> valueType) {
		return Double.class.equals(valueType) || Double.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Double> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(final Double expectedValue, final Double actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.doubleValue() == actualValue.doubleValue();
		}
		return true;
	}
}
