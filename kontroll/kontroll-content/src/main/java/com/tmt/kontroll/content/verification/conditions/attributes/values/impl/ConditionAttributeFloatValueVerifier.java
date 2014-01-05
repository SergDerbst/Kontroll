package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class ConditionAttributeFloatValueVerifier extends ConditionAttributeValueVerifier<Float> {

	private static class FloatComparator implements Comparator<Float> {
		@Override
		public int compare(final Float o1, final Float o2) {
			return (int) Math.ceil(o1.floatValue() - o2.floatValue());
		}
	}

	static final FloatComparator comparator = new FloatComparator();

	@Override
	protected boolean isResponsible(final Class<?> valueType) {
		return Float.class.equals(valueType) || Float.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Float> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(final Float expectedValue, final Float actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.floatValue() == actualValue.floatValue();
		}
		return true;
	}
}
