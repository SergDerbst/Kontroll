package com.tmt.kontroll.content.verification.conditions.attributes.values.impl;

import java.util.Comparator;

import com.tmt.kontroll.content.verification.conditions.attributes.values.ConditionAttributeValueVerifier;

public class CharacterConditionAttributeVerifier extends ConditionAttributeValueVerifier<Character> {
	
	private static class CharacterComparator implements Comparator<Character> {
		@Override
		public int compare(Character o1, Character o2) {
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
	protected boolean isResponsible(Class<?> valueType) {
		return Character.class.equals(valueType) || Character.TYPE.equals(valueType);
	}

	@Override
	protected Comparator<Character> getComparator() {
		return comparator;
	}

	@Override
	protected boolean isEqual(Character expectedValue, Character actualValue) {
		return super.checkNullEquality(expectedValue, actualValue) || expectedValue == actualValue;
	}
}
