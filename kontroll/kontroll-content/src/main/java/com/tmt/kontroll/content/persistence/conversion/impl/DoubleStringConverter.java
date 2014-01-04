package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class DoubleStringConverter extends ValueStringConverter<Double> {
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Double.class.equals(valueType) || Double.TYPE.equals(valueType);
	}

	@Override
	protected String doConvertToString(Double value) {
		return String.valueOf(value);
	}

	@Override
	protected Double doConvertToValue(String string) {
		return Double.parseDouble(string);
	}
}
