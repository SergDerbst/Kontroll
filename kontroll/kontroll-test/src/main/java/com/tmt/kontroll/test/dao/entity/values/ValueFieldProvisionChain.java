package com.tmt.kontroll.test.dao.entity.values;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.impl.BooleanFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.ByteFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.CharacterFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.DoubleFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.FloatFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.IntegerFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.LocaleFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.LongFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.ShortFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.StringFieldValueProvider;
import com.tmt.kontroll.test.dao.entity.values.impl.TimestampFieldValueProvider;

@Component
public class ValueFieldProvisionChain {

	@Autowired
	BooleanFieldValueProvider booleanFieldValueProvider;
	@Autowired
	ByteFieldValueProvider byteFieldValueProvider;
	@Autowired
	CharacterFieldValueProvider characterFieldValueProvider;
	@Autowired
	DoubleFieldValueProvider doubleFieldValueProvider;
	@Autowired
	FloatFieldValueProvider floatFieldValueProvider;
	@Autowired
	IntegerFieldValueProvider integerFieldValueProvider;
	@Autowired
	LongFieldValueProvider longFieldValueProvider;
	@Autowired
	ShortFieldValueProvider shortFieldValueProvider;
	@Autowired
	StringFieldValueProvider stringFieldValueProvider;
	@Autowired
	TimestampFieldValueProvider timestampFieldValueProvider;
	@Autowired
	LocaleFieldValueProvider localeFieldValueProvider;

	ValueFieldProvider<?> firstProvider;

	@PostConstruct
	public void setUpValueProvisionChain() {
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

	public Object provide(final String fieldName, final Class<?> valueClass) {
		return this.firstProvider.provide(fieldName, valueClass);
	}

	public void offset(final String fieldName, final Object offsetValue) {
		this.firstProvider.offset(fieldName, offsetValue);
	}

	public void increase(final int steps) {
		this.firstProvider.increase(steps);
	}

	public void init(final String fieldName, final Object initialValue) {
		this.firstProvider.init(fieldName, initialValue);
	}

	public void reset() {
		this.firstProvider.reset();
	}

	public void addValueProvider(final ValueFieldProvider<?> valueProvider) {
		valueProvider.setNextProvider(this.firstProvider);
		this.firstProvider = valueProvider;
	}
}