package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class ShortValueProvider extends SimpleValueProvider<Short> {

	public ShortValueProvider() {
		super((short) 0);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Short.class.equals(valueType) || Short.TYPE.equals(valueType);
	}

	@Override
	public Short makeNextDefaultValue(final Short value) {
		return (short) (value + 1);
	}
}
