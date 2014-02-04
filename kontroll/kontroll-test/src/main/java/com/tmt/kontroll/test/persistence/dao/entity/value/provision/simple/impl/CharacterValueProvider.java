package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class CharacterValueProvider extends SimpleValueProvider<Character> {

	public CharacterValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Character instantiateDefaultValue(final Class<?>... types) {
		return "0".charAt(0);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Character.class.equals(valueType) || Character.TYPE.equals(valueType);
	}

	@Override
	public Character makeNextDefaultValue(final Character value) {
		return this.combineValue(value, "1".charAt(0));
	}

	private Character combineValue(final Character valueOne, final Character valueTwo) {
		return String.valueOf(Integer.parseInt(String.valueOf(valueOne)) + Integer.parseInt(String.valueOf(valueTwo)) % 10).charAt(0);
	}
}
