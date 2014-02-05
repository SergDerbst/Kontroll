package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.SimpleValueProvider;

public class ShortValueProvider extends SimpleValueProvider<Short> {

	public ShortValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Short instantiateDefaultValue(final Class<?>... types) {
		return (short) 0;
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
