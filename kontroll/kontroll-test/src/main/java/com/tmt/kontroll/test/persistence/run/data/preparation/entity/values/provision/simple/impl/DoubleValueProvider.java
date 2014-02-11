package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class DoubleValueProvider extends SimpleValueProvider<Double> {

	public DoubleValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Double instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return 0.0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Field field,
	                                                 final Class<?> valueType) {
		return !field.isAnnotationPresent(Id.class) && Double.class.equals(valueType) || Double.TYPE.equals(valueType);
	}

	@Override
	public Double makeNextDefaultValue(final Object entity, final Field field, final Double value) {
		return value + 1;
	}
}
