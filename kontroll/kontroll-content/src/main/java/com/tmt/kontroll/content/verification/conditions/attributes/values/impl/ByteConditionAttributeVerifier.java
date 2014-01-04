package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class ByteConditionAttributeVerifier extends ConditionAttributeValueVerifier<Byte> {
	
	private static class ByteComparator implements Comparator<Byte> {
		@Override
		public int compare(Byte o1, Byte o2) {
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
	protected boolean isResponsible(Class<?> valueType) {
		return Byte.class.equals(valueType) || Byte.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Byte> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(Byte expectedValue, Byte actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue == actualValue;
	}
}
