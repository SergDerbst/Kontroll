package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class StringStringConverter extends ValueStringConverter<String> {

	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return String.class.equals(valueType);
	}
	
	@Override
	protected String doConvertToString(String value) {
		return value;
	}

	@Override
	protected String doConvertToValue(String string) {
		return string;
	}
}
