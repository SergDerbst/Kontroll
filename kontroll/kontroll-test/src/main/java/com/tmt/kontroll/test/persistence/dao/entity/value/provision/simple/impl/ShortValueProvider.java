package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class ShortValueProvider extends SimpleValueProvider<Short> {

	public ShortValueProvider() {
		super((short) 0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Short.class.equals(valueClass) || Short.TYPE.equals(valueClass);
	}

	@Override
	public Short makeNextValue(final Short value) {
		return (short) (value + 1);
	}
}
