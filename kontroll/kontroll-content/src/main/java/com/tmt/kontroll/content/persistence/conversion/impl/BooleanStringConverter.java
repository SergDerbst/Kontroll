package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class BooleanStringConverter extends ValueStringConverter<Boolean> {
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Boolean.class.equals(valueType) || Boolean.TYPE.equals(valueType);
	}

	@Override
	protected String doConvertToString(Boolean value) {
		return String.valueOf(value);
	}

	@Override
	protected Boolean doConvertToValue(String string) {
		return Boolean.parseBoolean(string);
	}
}