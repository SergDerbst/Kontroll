package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.BooleanValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.ByteValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.CharacterValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.DoubleValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.FloatValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.IntegerValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.LocaleValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.LongValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.ShortValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.StringValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.TimestampValueProvider;

@Component
public class SimpleValueProvisionHandler {

	@Autowired
	BooleanValueProvider booleanFieldValueProvider;
	@Autowired
	ByteValueProvider byteFieldValueProvider;
	@Autowired
	CharacterValueProvider characterFieldValueProvider;
	@Autowired
	DoubleValueProvider doubleFieldValueProvider;
	@Autowired
	FloatValueProvider floatFieldValueProvider;
	@Autowired
	IntegerValueProvider integerFieldValueProvider;
	@Autowired
	LongValueProvider longFieldValueProvider;
	@Autowired
	ShortValueProvider shortFieldValueProvider;
	@Autowired
	StringValueProvider stringFieldValueProvider;
	@Autowired
	TimestampValueProvider timestampFieldValueProvider;
	@Autowired
	LocaleValueProvider localeFieldValueProvider;

	SimpleValueProvider<?> firstProvider;

	@PostConstruct
	public void setUpValueProvisionHandler() {
		this.firstProvider = this.booleanFieldValueProvider;
		this.booleanFieldValueProvider.setNextProvider(this.byteFieldValueProvider);
		this.byteFieldValueProvider.setNextProvider(this.characterFieldValueProvider);
		this.characterFieldValueProvider.setNextProvider(this.doubleFieldValueProvider);
		this.doubleFieldValueProvider.setNextProvider(this.floatFieldValueProvider);
		this.floatFieldValueProvider.setNextProvider(this.integerFieldValueProvider);
		this.integerFieldValueProvider.setNextProvider(this.longFieldValueProvider);
		this.longFieldValueProvider.setNextProvider(this.shortFieldValueProvider);
		this.shortFieldValueProvider.setNextProvider(this.stringFieldValueProvider);
		this.stringFieldValueProvider.setNextProvider(this.timestampFieldValueProvider);
		this.timestampFieldValueProvider.setNextProvider(this.localeFieldValueProvider);
		this.localeFieldValueProvider.setNextProvider(null);
	}

	public Object provide(final Class<?> valueType) {
		return this.provide("", valueType);
	}

	public Object provide(final String fieldName, final Class<?> valueType) {
		return this.firstProvider.provide(fieldName, valueType);
	}

	public void init(final String fieldName, final Object value) {
		this.firstProvider.init(fieldName, value);
	}

	public void reset() {
		this.firstProvider.reset();
	}

	public Object fetchNextValue(final Object value) {
		return this.fetchNextValue("", value);
	}

	public Object fetchNextValue(final String fieldName, final Object value) {
		return this.firstProvider.fetchNextValue(fieldName, value);
	}

	public void addValueProvider(final SimpleValueProvider<?> valueProvider) {
		valueProvider.setNextProvider(this.firstProvider);
		this.firstProvider = valueProvider;
	}
}