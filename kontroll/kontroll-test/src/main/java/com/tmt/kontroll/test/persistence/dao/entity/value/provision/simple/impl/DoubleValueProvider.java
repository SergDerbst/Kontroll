package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class DoubleValueProvider extends SimpleValueProvider<Double> {

	public DoubleValueProvider() {
		super(0.0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Double.class.equals(valueClass) || Double.TYPE.equals(valueClass);
	}

	@Override
	public Double makeNextValue(final Double value) {
		return value + 1;
	}
}
