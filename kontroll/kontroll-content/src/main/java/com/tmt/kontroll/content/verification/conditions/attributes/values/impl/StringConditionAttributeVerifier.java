package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class StringConditionAttributeVerifier extends ConditionAttributeValueVerifier<String> {

	private static class StringComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	}
	
	static final StringComparator comparator = new StringComparator();
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return String.class.equals(valueType);
	}

	@Override
	protected Comparator<String> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(String expectedValue, String actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue.equals(actualValue);
	}
}
