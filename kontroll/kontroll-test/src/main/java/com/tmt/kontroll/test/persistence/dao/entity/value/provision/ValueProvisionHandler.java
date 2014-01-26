package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl.ListValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl.SetValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl.SortedSetValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl.DefaultMapValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl.SortedMapValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.EntityValueProvider;
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
public class ValueProvisionHandler {

	//simples
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
	EntityValueProvider entityValueProvider;
	@Autowired
	EnumValueProvider enumValueProvider;
	@Autowired
	TimestampValueProvider timestampValueProvider;
	@Autowired
	LocaleValueProvider localeValueProvider;

	//collections
	@Autowired
	ListValueProvider listValueProvider;
	@Autowired
	SetValueProvider setValueProvider;
	@Autowired
	SortedSetValueProvider sortedSetValueProvider;

	//maps
	@Autowired
	DefaultMapValueProvider mapValueProvider;
	@Autowired
	SortedMapValueProvider sortedMapValueProvider;

	@Autowired
	ValueProvisionHandlingPreparator provisionPreparator;

	private ValueProvider<?> firstProvider;

	@PostConstruct
	public void setUpValueProvision() {
		this.firstProvider = this.entityValueProvider;
		this.entityValueProvider.setNextProvider(this.booleanValueProvider);
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
		this.localeValueProvider.setNextProvider(this.listValueProvider);
		this.listValueProvider.setNextProvider(this.setValueProvider);
		this.setValueProvider.setNextProvider(this.sortedSetValueProvider);
		this.sortedSetValueProvider.setNextProvider(this.mapValueProvider);
		this.mapValueProvider.setNextProvider(this.sortedMapValueProvider);
		this.sortedMapValueProvider.setNextProvider(null);
	}

	public boolean canProvideValue(final String fieldName, final Class<?>... types) {
		return this.firstProvider.canProvideValue(fieldName, types);
	}

	public Object provide(final Class<?>... types) {
		return this.provide("", types);
	}

	public Object provide(final String fieldName, final Class<?>... types) {
		this.provisionPreparator.prepare(fieldName, types);
		return this.firstProvider.provide(fieldName, types);
	}

	public Object fetchNextValue(final Object value) {
		return this.fetchNextValue("", value);
	}

	public Object fetchNextValue(final String fieldName, final Object value) {
		return this.firstProvider.fetchNextValue(fieldName, value);
	}

	public void reset() {
		this.firstProvider.reset();
	}

	public void addValueProvider(final ValueProvider<?> valueProvider) {
		valueProvider.setNextProvider(this.firstProvider);
		this.firstProvider = valueProvider;
	}

	public ValueProvider<?> getFirstProvider() {
		return this.firstProvider;
	}

	protected void setFirstProvider(final ValueProvider<?> firstProvider) {
		this.firstProvider = firstProvider;
	}
}
