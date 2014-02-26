package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class DoubleValueProvider extends SimpleValueProvider<Double> {

	public DoubleValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Double instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return 0.0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind,
	                                                 final Class<?> valueType) {
		return
		ValueProvisionKind.Id != kind &&
		(Double.class.equals(valueType) || Double.TYPE.equals(valueType));
	}

	@Override
	public Double makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Double value, final Class<?>... types) {
		return value + 1;
	}
}
