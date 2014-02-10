package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class FloatValueProvider extends SimpleValueProvider<Float> {

	public FloatValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Float instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return (float) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Float.class.equals(valueType) || Float.TYPE.equals(valueType);
	}

	@Override
	public Float makeNextDefaultValue(final Object entity, final Field field, final Float value) {
		return value + 1;
	}
}
