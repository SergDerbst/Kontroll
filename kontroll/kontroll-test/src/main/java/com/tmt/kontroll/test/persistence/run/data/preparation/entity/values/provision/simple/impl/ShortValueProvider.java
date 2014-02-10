package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class ShortValueProvider extends SimpleValueProvider<Short> {

	public ShortValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Short instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return (short) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Short.class.equals(valueType) || Short.TYPE.equals(valueType);
	}

	@Override
	public Short makeNextDefaultValue(final Object entity, final Field field, final Short value) {
		return (short) (value + 1);
	}
}
