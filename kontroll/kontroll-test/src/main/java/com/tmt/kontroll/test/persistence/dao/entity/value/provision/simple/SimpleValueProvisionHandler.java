package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.EntityValueProviderFactory;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.BooleanValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.ByteValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.CharacterValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.DoubleValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl.EnumValueProvider;
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
	EntityValueProviderFactory entityValueProviderFactory;

	@Autowired
	BooleanValueProvider booleanValueProvider;
	@Autowired
	ByteValueProvider byteValueProvider;
	@Autowired
	CharacterValueProvider characterValueProvider;
	@Autowired
	DoubleValueProvider doubleValueProvider;
	@Autowired
	FloatValueProvider floatValueProvider;
	@Autowired
	IntegerValueProvider integerValueProvider;
	@Autowired
	LongValueProvider longValueProvider;
	@Autowired
	ShortValueProvider shortValueProvider;
	@Autowired
	StringValueProvider stringValueProvider;

	@Autowired
	EnumValueProvider enumValueProvider;

	@Autowired
	TimestampValueProvider timestampValueProvider;
	@Autowired
	LocaleValueProvider localeValueProvider;

	SimpleValueProvider<?> firstProvider;

	@PostConstruct
	public void setUpValueProvisionHandler() {
		this.firstProvider = this.entityValueProviderFactory.create();
		this.firstProvider.setNextProvider(this.booleanValueProvider);
		this.booleanValueProvider.setNextProvider(this.byteValueProvider);
		this.byteValueProvider.setNextProvider(this.characterValueProvider);
		this.characterValueProvider.setNextProvider(this.doubleValueProvider);
		this.doubleValueProvider.setNextProvider(this.floatValueProvider);
		this.floatValueProvider.setNextProvider(this.integerValueProvider);
		this.integerValueProvider.setNextProvider(this.longValueProvider);
		this.longValueProvider.setNextProvider(this.shortValueProvider);
		this.shortValueProvider.setNextProvider(this.stringValueProvider);
		this.stringValueProvider.setNextProvider(this.enumValueProvider);
		this.enumValueProvider.setNextProvider(this.timestampValueProvider);
		this.timestampValueProvider.setNextProvider(this.localeValueProvider);
		this.localeValueProvider.setNextProvider(null);
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