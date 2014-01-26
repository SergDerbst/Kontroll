package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class DoubleValueProvider extends SimpleValueProvider<Double> {

	@Override
	protected Double instantiateDefaultValue(final Class<?>... types) {
		return 0.0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Double.class.equals(valueType) || Double.TYPE.equals(valueType);
	}

	@Override
	public Double makeNextDefaultValue(final Double value) {
		return value + 1;
	}
}
