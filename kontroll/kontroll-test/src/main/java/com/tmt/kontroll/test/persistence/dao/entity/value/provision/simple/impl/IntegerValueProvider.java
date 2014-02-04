package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class IntegerValueProvider extends SimpleValueProvider<Integer> {

	public IntegerValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Integer instantiateDefaultValue(final Class<?>... types) {
		return 0;
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
