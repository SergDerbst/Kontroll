package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class FloatConditionAttributeVerifier extends ConditionAttributeValueVerifier<Float> {

	private static class FloatComparator implements Comparator<Float> {
		@Override
		public int compare(Float o1, Float o2) {
			return (int) Math.ceil(o1.floatValue() - o2.floatValue());
		}
	}
	
	static final FloatComparator comparator = new FloatComparator();
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Float.class.equals(valueType) || Float.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Float> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(Float expectedValue, Float actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue.floatValue() == actualValue.floatValue();
	}
}
