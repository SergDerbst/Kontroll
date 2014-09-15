package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class IntegerStringConverter extends ValueStringConverter<Integer> {
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Integer.class.equals(valueType) || Integer.TYPE.equals(valueType);
	}

	@Override
	protected String doConvertToString(Integer value) {
		return String.valueOf(value);
	}

	@Override
	protected Integer doConvertToValue(String string) {
		return Integer.parseInt(string);
	}
}
