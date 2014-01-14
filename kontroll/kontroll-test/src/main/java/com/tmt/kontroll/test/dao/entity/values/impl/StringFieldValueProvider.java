package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class StringFieldValueProvider extends ValueFieldProvider<String> {

	public StringFieldValueProvider() {
		super("0");
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return String.class.equals(valueClass);
	}

	@Override
	protected String combineValue(final String valueOne, final String valueTwo) {
		return valueOne + valueTwo;
	}

	@Override
	protected void increase() {
		super.setCurrentValue(String.valueOf(Integer.parseInt(super.getCurrentValue()) + 1));
	}
}
