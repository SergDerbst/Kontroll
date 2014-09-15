package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class LongStringConverter extends ValueStringConverter<Long> {
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Long.class.equals(valueType) || Long.TYPE.equals(valueType);
	}

	@Override
	protected String doConvertToString(Long value) {
		return String.valueOf(value);
	}

	@Override
	protected Long doConvertToValue(String string) {
		return Long.parseLong(string);
	}
}
