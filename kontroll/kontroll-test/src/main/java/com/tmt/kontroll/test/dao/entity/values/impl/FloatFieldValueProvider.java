package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class FloatFieldValueProvider extends ValueFieldProvider<Float> {

	public FloatFieldValueProvider() {
		super((float) 0.0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Float.class.equals(valueClass) || Float.TYPE.equals(valueClass);
	}

	@Override
	protected Float combineValue(final Float valueOne, final Float valueTwo) {
		return valueOne + valueTwo;
	}

	@Override
	protected void increase() {
		super.setCurrentValue(super.getCurrentValue() + 1);
	}
}
