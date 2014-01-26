package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

@Component
public class ConditionAttributeLongValueVerifier extends ConditionAttributeValueVerifier<Long> {

	private static class LongComparator implements Comparator<Long> {
		@Override
		public int compare(final Long o1, final Long o2) {
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
	protected boolean isResponsible(final Class<?> valueType) {
		return Long.class.equals(valueType) || Long.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Long> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(final Long expectedValue, final Long actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.longValue() == actualValue.longValue();
		}
		return true;
	}
}
