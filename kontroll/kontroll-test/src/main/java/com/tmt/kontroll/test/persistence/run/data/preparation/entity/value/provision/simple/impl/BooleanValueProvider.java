package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.SimpleValueProvider;

public class BooleanValueProvider extends SimpleValueProvider<Boolean> {

	public BooleanValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Boolean instantiateDefaultValue(final Class<?>... types) {
		return false;
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
