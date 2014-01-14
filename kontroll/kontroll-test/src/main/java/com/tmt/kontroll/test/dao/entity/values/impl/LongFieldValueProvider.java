package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class LongFieldValueProvider extends ValueFieldProvider<Long> {

	public LongFieldValueProvider() {
		super((long) 0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Long.class.equals(valueClass) || Long.TYPE.equals(valueClass);
	}

	@Override
	protected Long combineValue(final Long valueOne, final Long valueTwo) {
		return valueOne + valueTwo;
	}

	@Override
	protected void increase() {
		super.setCurrentValue(super.getCurrentValue() + 1);
	}
}
