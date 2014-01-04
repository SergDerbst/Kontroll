package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class LongConditionAttributeVerifier extends ConditionAttributeValueVerifier<Long> {

	private static class LongComparator implements Comparator<Long> {
		@Override
		public int compare(Long o1, Long o2) {
			if (o1.longValue() == o2.longValue()) {
				return 0;
			}
			if (o1.longValue() - o2.longValue() < 0) {
				return -1;
			}
			return 1;
		}
	}
	
	static final LongComparator comparator = new LongComparator();
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Long.class.equals(valueType) || Long.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Long> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(Long expectedValue, Long actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue.longValue() == actualValue.longValue();
	}
}
