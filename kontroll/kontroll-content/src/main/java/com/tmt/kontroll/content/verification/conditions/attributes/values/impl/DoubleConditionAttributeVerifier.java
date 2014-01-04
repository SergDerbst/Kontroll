package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class DoubleConditionAttributeVerifier extends ConditionAttributeValueVerifier<Double> {

	private static class DoubleComparator implements Comparator<Double> {
		@Override
		public int compare(Double o1, Double o2) {
			return (int) Math.ceil(o1.doubleValue() - o2.doubleValue());
		}
	}
	
	static final DoubleComparator comparator = new DoubleComparator();
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Double.class.equals(valueType) || Double.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Double> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(Double expectedValue, Double actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue.doubleValue() == actualValue.doubleValue();
	}
}
