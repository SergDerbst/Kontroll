package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class ShortConditionAttributeVerifier extends ConditionAttributeValueVerifier<Short> {

	private static class ShortComparator implements Comparator<Short> {
		@Override
		public int compare(Short o1, Short o2) {
			return o1.shortValue() - o2.shortValue();
		}
	}
	
	static final ShortComparator comparator = new ShortComparator();
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Short.class.equals(valueType) || Short.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Short> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(Short expectedValue, Short actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue.shortValue() == actualValue.shortValue();
	}
}
