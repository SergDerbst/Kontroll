package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class IntegerValueProvider extends SimpleValueProvider<Integer> {

	public IntegerValueProvider() {
		super(0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Integer.class.equals(valueClass) || Integer.TYPE.equals(valueClass);
	}

	@Override
	public Integer makeNextValue(final Integer value) {
		return value + 1;
	}
}
