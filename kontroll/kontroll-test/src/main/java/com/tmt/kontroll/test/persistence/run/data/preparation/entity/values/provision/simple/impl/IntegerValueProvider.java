package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class IntegerValueProvider extends SimpleValueProvider<Integer> {

	public IntegerValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Integer instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Integer.class.equals(valueType) || Integer.TYPE.equals(valueType);
	}

	@Override
	public Integer makeNextDefaultValue(final Object entity, final Field field, final Integer value) {
		return value + 1;
	}
}
