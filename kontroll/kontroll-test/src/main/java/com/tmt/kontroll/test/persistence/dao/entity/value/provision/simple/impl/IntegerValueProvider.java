package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class IntegerValueProvider extends SimpleValueProvider<Integer> {

	public IntegerValueProvider() {
		super(0);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Integer.class.equals(valueType) || Integer.TYPE.equals(valueType);
	}

	@Override
	public Integer makeNextDefaultValue(final Integer value) {
		return value + 1;
	}
}
