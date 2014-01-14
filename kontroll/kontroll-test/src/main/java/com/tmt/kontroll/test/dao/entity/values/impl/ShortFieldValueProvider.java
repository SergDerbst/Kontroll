package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class ShortFieldValueProvider extends ValueFieldProvider<Short> {

	public ShortFieldValueProvider() {
		super((short) 0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Short.class.equals(valueClass) || Short.TYPE.equals(valueClass);
	}

	@Override
	protected Short combineValue(final Short valueOne, final Short valueTwo) {
		return (short) (valueOne + valueTwo);
	}

	@Override
	protected void increase() {
		super.setCurrentValue((short) (super.getCurrentValue() + 1));
	}
}
