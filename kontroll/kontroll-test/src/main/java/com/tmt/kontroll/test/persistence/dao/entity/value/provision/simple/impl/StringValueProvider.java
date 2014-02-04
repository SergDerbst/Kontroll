package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class StringValueProvider extends SimpleValueProvider<String> {

	public StringValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected String instantiateDefaultValue(final Class<?>... types) {
		return "0";
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return String.class.equals(valueType);
	}

	@Override
	public String makeNextDefaultValue(final String value) {
		return String.valueOf(Integer.parseInt(value) + 1);
	}
}
