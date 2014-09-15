package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class CharacterValueProvider extends SimpleValueProvider<Character> {

	public CharacterValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Character instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return "0".charAt(0);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind,
	                                                 final Class<?> valueType) {
		return
		ValueProvisionKind.Id != kind &&
		(Character.class.equals(valueType) || Character.TYPE.equals(valueType));
	}

	@Override
	public Character makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Character value, final Class<?>... types) {
		return this.combineValue(value, "1".charAt(0));
	}

	private Character combineValue(final Character valueOne, final Character valueTwo) {
		return String.valueOf(Integer.parseInt(String.valueOf(valueOne)) + Integer.parseInt(String.valueOf(valueTwo)) % 10).charAt(0);
	}
}
