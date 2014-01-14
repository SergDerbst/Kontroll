package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class BooleanFieldValueProvider extends ValueFieldProvider<Boolean> {

	public BooleanFieldValueProvider() {
		super(false);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Boolean.class.equals(valueClass) || Boolean.TYPE.equals(valueClass);
	}

	@Override
	protected Boolean combineValue(final Boolean valueOne, final Boolean valueTwo) {
		return valueOne && valueTwo;
	}

	@Override
	protected void increase() {
		super.setCurrentValue(!super.getCurrentValue());
	}
}
