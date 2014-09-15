package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class FloatValueProvider extends SimpleValueProvider<Float> {

	public FloatValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Float instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return (float) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind,
	                                                 final Class<?> valueType) {
		return
		ValueProvisionKind.Id != kind &&
		(Float.class.equals(valueType) || Float.TYPE.equals(valueType));
	}

	@Override
	public Float makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Float value, final Class<?>... types) {
		return value + 1;
	}
}
