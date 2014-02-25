package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class StringValueProvider extends SimpleValueProvider<String> {

	public StringValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected String instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return "0";
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind,
	                                                 final Class<?> valueType) {
		return ValueProvisionKind.Id != kind && String.class.equals(valueType);
	}

	@Override
	public String makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final String value) {
		return String.valueOf(Integer.parseInt(value) + 1);
	}
}
