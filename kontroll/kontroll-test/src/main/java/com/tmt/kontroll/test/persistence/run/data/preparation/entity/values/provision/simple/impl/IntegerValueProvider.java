package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class IntegerValueProvider extends SimpleValueProvider<Integer> {

	public IntegerValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Integer instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind,
	                                                 final Class<?> valueType) {
		return
		ValueProvisionKind.Id != kind &&
		(Integer.class.equals(valueType) || Integer.TYPE.equals(valueType));
	}

	@Override
	public Integer makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Integer value, final Class<?>... types) {
		return value + 1;
	}
}
