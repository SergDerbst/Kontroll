package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class IntegerFieldValueProvider extends ValueFieldProvider<Integer> {

	public IntegerFieldValueProvider() {
		super(0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Integer.class.equals(valueClass) || Integer.TYPE.equals(valueClass);
	}

	@Override
	protected Integer combineValue(final Integer valueOne, final Integer valueTwo) {
		return valueOne + valueTwo;
	}

	@Override
	protected void increase() {
		super.setCurrentValue(super.getCurrentValue() + 1);
	}
}
