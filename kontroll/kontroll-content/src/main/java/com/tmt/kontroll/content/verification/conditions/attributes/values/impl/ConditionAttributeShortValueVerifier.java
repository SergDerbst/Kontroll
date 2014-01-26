package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

@Component
public class ConditionAttributeShortValueVerifier extends ConditionAttributeValueVerifier<Short> {

	private static class ShortComparator implements Comparator<Short> {
		@Override
		public int compare(final Short o1, final Short o2) {
			return o1.shortValue() - o2.shortValue();
		}
	}

	static final ShortComparator comparator = new ShortComparator();

	@Override
	protected boolean isResponsible(final Class<?> valueType) {
		return Short.class.equals(valueType) || Short.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Short> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(final Short expectedValue, final Short actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.shortValue() == actualValue.shortValue();
		}
		return true;
	}
}
