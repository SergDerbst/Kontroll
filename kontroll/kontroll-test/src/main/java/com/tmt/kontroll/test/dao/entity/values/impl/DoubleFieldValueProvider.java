package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class DoubleFieldValueProvider extends ValueFieldProvider<Double> {

	public DoubleFieldValueProvider() {
		super(0.0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Double.class.equals(valueClass) || Double.TYPE.equals(valueClass);
	}

	@Override
	protected Double combineValue(final Double valueOne, final Double valueTwo) {
		return valueOne + valueTwo;
	}

	@Override
	protected void increase() {
		super.setCurrentValue(super.getCurrentValue() + 1);
	}
}
