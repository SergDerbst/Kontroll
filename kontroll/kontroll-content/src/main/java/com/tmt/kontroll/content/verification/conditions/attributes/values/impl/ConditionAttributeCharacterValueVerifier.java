package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class ConditionAttributeCharacterValueVerifier extends ConditionAttributeValueVerifier<Character> {

	private static class CharacterComparator implements Comparator<Character> {
		@Override
		public int compare(final Character o1, final Character o2) {
			if (o1.charValue() == o2.charValue()) {
				return 0;
			}
			if (o1.charValue() < o2.charValue()) {
				return -1;
			}
			return 1;
		}
	}

	static final CharacterComparator comparator = new CharacterComparator();

	@Override
	protected boolean isResponsible(final Class<?> valueType) {
		return Character.class.equals(valueType) || Character.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Character> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(final Character expectedValue, final Character actualValue) {
		if (!super.checkNullEquality(expectedValue, actualValue)) {
			return expectedValue != null && actualValue != null && expectedValue.charValue() == actualValue.charValue();
		}
		return true;
	}
}
