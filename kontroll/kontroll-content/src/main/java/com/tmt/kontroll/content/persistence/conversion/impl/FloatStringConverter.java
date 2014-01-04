package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class FloatStringConverter extends ValueStringConverter<Float> {
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Float.class.equals(valueType) || Float.TYPE.equals(valueType);
	}

	@Override
	protected String doConvertToString(Float value) {
		return String.valueOf(value);
	}

	@Override
	protected Float doConvertToValue(String string) {
		return Float.parseFloat(string);
	}
}
