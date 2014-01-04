package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class ShortStringConverter extends ValueStringConverter<Short> {
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Short.class.equals(valueType) || Short.TYPE.equals(valueType);
	}

	@Override
	protected String doConvertToString(Short value) {
		return String.valueOf(value);
	}

	@Override
	protected Short doConvertToValue(String string) {
		return Short.parseShort(string);
	}
}
