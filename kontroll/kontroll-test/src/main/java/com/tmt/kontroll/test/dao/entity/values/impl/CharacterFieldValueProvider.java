package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class CharacterFieldValueProvider extends ValueFieldProvider<Character> {

	public CharacterFieldValueProvider() {
		super("0".charAt(0));
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Character.class.equals(valueClass) || Character.TYPE.equals(valueClass);
	}

	@Override
	protected Character combineValue(final Character valueOne, final Character valueTwo) {
		return String.valueOf(Integer.parseInt(String.valueOf(valueOne)) + Integer.parseInt(String.valueOf(valueTwo)) % 10).charAt(0);
	}

	@Override
	protected void increase() {
		super.setCurrentValue(this.combineValue(super.getCurrentValue(), "1".charAt(0)));
	}
}
