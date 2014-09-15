package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

@Component
public class ConditionAttributeStringValueVerifier extends ConditionAttributeValueVerifier<String> {

	private static class StringComparator implements Comparator<String> {
		@Override
		public int compare(final String o1, final String o2) {
			return o1.compareTo(o2);
		}
	}

	static final StringComparator comparator = new StringComparator();

	@Override
	protected boolean isResponsible(final Class<?> valueType) {
		return String.class.equals(valueType);
	}

	@Override
	protected Comparator<String> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(final String expectedValue, final String actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.equals(actualValue);
		}
		return true;
	}
}
