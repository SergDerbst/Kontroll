package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class BooleanValueProvider extends SimpleValueProvider<Boolean> {

	protected BooleanValueProvider() {
		super(false);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Boolean.class.equals(valueType) || Boolean.TYPE.equals(valueType);
	}

	@Override
	public Boolean makeNextDefaultValue(final Boolean value) {
		return !value;
	}
}
