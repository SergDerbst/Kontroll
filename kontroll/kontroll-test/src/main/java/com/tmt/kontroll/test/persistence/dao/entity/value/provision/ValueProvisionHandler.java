package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

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

public class ValueProvisionHandler {

	private static class InstanceHolder {
		public static ValueProvisionHandler instance = new ValueProvisionHandler();
	}

	public static ValueProvisionHandler instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ValueProvisionHandler();
		}
		return InstanceHolder.instance;
	}

	private ValueProvider<?> firstProvider;

	public ValueProvisionHandler() {
		this.setUpValueProvision();
	}

	public void setUpValueProvision() {
		this.firstProvider = EntityValueProvider.instance();
		this.firstProvider
		.setNextProvider(BooleanValueProvider.instance())
		.setNextProvider(ByteValueProvider.instance())
		.setNextProvider(CharacterValueProvider.instance())
		.setNextProvider(DoubleValueProvider.instance())
		.setNextProvider(FloatValueProvider.instance())
		.setNextProvider(IntegerValueProvider.instance())
		.setNextProvider(LongValueProvider.instance())
		.setNextProvider(ShortValueProvider.instance())
		.setNextProvider(StringValueProvider.instance())
		.setNextProvider(EnumValueProvider.instance())
		.setNextProvider(TimestampValueProvider.instance())
		.setNextProvider(LocaleValueProvider.instance())
		.setNextProvider(ListValueProvider.instance())
		.setNextProvider(SetValueProvider.instance())
		.setNextProvider(SortedSetValueProvider.instance())
		.setNextProvider(DefaultMapValueProvider.instance())
		.setNextProvider(SortedMapValueProvider.instance());
	}

	public boolean canProvideValue(final String fieldName, final Class<?>... types) {
		return this.firstProvider.canProvideValue(fieldName, types);
	}

	public Object provide(final Class<?>... types) {
		return this.provide("", types);
	}

	public Object provide(final String fieldName, final Class<?>... types) {
		ValueProvisionHandlingPreparator.instance().prepare(fieldName, types);
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
