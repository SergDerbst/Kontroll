package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class ShortValueProvider extends SimpleValueProvider<Short> {

	public ShortValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Short instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return (short) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind, final Class<?> valueType) {
		return
		ValueProvisionKind.Id != kind &&
		(Short.class.equals(valueType) || Short.TYPE.equals(valueType));
	}

	@Override
	public Short makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Short value, final Class<?>... types) {
		return (short) (value + 1);
	}
}
