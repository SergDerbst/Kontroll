package com.tmt.kontroll.content.persistence.conversion;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.content.persistence.conversion.impl.BooleanStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.ByteStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.CharacterStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.DoubleStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.FloatStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.IntegerStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.LongStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.ShortStringConverter;
import com.tmt.kontroll.content.persistence.conversion.impl.StringStringConverter;

@Component
public class ValueStringConversionChain {

	@Autowired
	BooleanStringConverter booleanStringConverter;
	@Autowired
	ByteStringConverter byteStringConverter;
	@Autowired
	CharacterStringConverter characterStringConverter;
	@Autowired
	DoubleStringConverter doubleStringConverter;
	@Autowired
	FloatStringConverter floatStringConverter;
	@Autowired
	IntegerStringConverter integerStringConverter;
	@Autowired
	LongStringConverter longStringConverter;
	@Autowired
	ShortStringConverter shortStringConverter;
	@Autowired
	StringStringConverter stringStringConverter;
	
	@PostConstruct
	public void setUpConversionChain() {
		this.stringStringConverter.setNextConverter(this.integerStringConverter);
		this.integerStringConverter.setNextConverter(this.booleanStringConverter);
		this.booleanStringConverter.setNextConverter(this.longStringConverter);
		this.longStringConverter.setNextConverter(this.doubleStringConverter);
		this.doubleStringConverter.setNextConverter(this.floatStringConverter);
		this.floatStringConverter.setNextConverter(this.shortStringConverter);
		this.shortStringConverter.setNextConverter(this.byteStringConverter);
		this.byteStringConverter.setNextConverter(this.characterStringConverter);
		this.characterStringConverter.setNextConverter(null);
	}
	
	public String convertToString(final Object value) {
		return this.stringStringConverter.convertToString(value);
	}
	
	public Object convertToValue(final String string, final Class<?> valueType) {
		return this.stringStringConverter.convertToValue(string, valueType);
	}
}
