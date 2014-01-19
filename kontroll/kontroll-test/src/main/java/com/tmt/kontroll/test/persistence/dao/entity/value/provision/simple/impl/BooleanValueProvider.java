package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class BooleanValueProvider extends SimpleValueProvider<Boolean> {

	protected BooleanValueProvider() {
		super(false);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Boolean.class.equals(valueClass) || Boolean.TYPE.equals(valueClass);
	}

	@Override
	public Boolean makeNextValue(final Boolean value) {
		return !value;
	}
}
