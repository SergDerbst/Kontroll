package com.tmt.kontroll.test.dao.entity.values.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class ByteFieldValueProvider extends ValueFieldProvider<Byte> {

	public ByteFieldValueProvider() {
		super(Byte.parseByte("0"));
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Byte.class.equals(valueClass) || Byte.TYPE.equals(valueClass);
	}

	@Override
	protected Byte combineValue(final Byte valueOne, final Byte valueTwo) {
		return Byte.parseByte(String.valueOf(Integer.parseInt(String.valueOf(valueOne)) + Integer.parseInt(String.valueOf(valueTwo))));
	}

	@Override
	protected void increase() {
		super.setCurrentValue(Byte.parseByte(String.valueOf(Integer.parseInt(String.valueOf(super.getCurrentValue())) + 1)));
	}
}
