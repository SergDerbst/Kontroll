package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class ByteStringConverter extends ValueStringConverter<Byte> {
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Byte.class.equals(valueType) || Byte.TYPE.equals(valueType);
	}

	@Override
	protected String doConvertToString(Byte value) {
		return String.valueOf(value);
	}

	@Override
	protected Byte doConvertToValue(String string) {
		return Byte.parseByte(string);
	}
}