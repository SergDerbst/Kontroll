package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

@Component
public class ConditionAttributeByteValueVerifier extends ConditionAttributeValueVerifier<Byte> {

	private static class ByteComparator implements Comparator<Byte> {
		@Override
		public int compare(final Byte o1, final Byte o2) {
			if (o1.byteValue() == o2.byteValue()) {
				return 0;
			}
			if (o1.byteValue() < o2.byteValue()) {
				return -1;
			}
			return 1;
		}
	}


	static final ByteComparator comparator = new ByteComparator();

	@Override
	protected boolean isResponsible(final Class<?> valueType) {
		return Byte.class.equals(valueType) || Byte.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Byte> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(final Byte expectedValue, final Byte actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.byteValue() == actualValue.byteValue();
		}
		return true;
	}
}
