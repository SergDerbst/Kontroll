package com.tmt.kontroll.content.persistence.conversion.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.ValueStringConverter;

@Component
public class CharacterStringConverter extends ValueStringConverter<Character> {
	
	@Override
	protected boolean isResponsible(Class<?> valueType) {
		return Character.class.equals(valueType) || Character.TYPE.equals(valueType);
	}

	@Override
	protected String doConvertToString(Character value) {
		return String.valueOf(value);
	}

	@Override
	protected Character doConvertToValue(String string) {
		if (string.length() > 1) {
			throw new RuntimeException("Cannot convert a string to a character when the string's length is larger than one.");
		}
		return string.charAt(0);
	}
}