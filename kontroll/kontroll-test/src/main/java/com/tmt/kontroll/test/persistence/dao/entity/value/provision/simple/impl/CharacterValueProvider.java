package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class CharacterValueProvider extends SimpleValueProvider<Character> {

	public CharacterValueProvider() {
		super("0".charAt(0));
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Character.class.equals(valueClass) || Character.TYPE.equals(valueClass);
	}

	@Override
	public Character makeNextValue(final Character value) {
		return this.combineValue(value, "1".charAt(0));
	}

	private Character combineValue(final Character valueOne, final Character valueTwo) {
		return String.valueOf(Integer.parseInt(String.valueOf(valueOne)) + Integer.parseInt(String.valueOf(valueTwo)) % 10).charAt(0);
	}
}
