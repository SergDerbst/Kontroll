package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class IntegerConditionAttributeVerifier extends ConditionAttributeValueVerifier<Integer> {

	private static class IntegerComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.intValue() - o2.intValue();
		}
	}
	
	static final IntegerComparator comparator = new IntegerComparator();
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Integer.class.equals(valueType) || Integer.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Integer> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(Integer expectedValue, Integer actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue.intValue() == actualValue.intValue();
	}
}
