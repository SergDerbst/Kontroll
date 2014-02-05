package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.collection.impl.ListValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.collection.impl.SetValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.collection.impl.SortedSetValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.map.impl.DefaultMapValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.map.impl.SortedMapValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.entity.EntityValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.BooleanValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.ByteValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.CharacterValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.DoubleValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.EnumValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.FloatValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.IntegerValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.LocaleValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.LongValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.ShortValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.StringValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.impl.TimestampValueProvider;

public class ValueProvisionHandler {

	private ValueProvider<?> firstProvider;

	public ValueProvisionHandler() {
		this.setUpValueProvision();
	}

	public void setUpValueProvision() {
		this.addValueProvider(new EntityValueProvider(this));
		this.addValueProvider(new BooleanValueProvider(this));
		this.addValueProvider(new ByteValueProvider(this));
		this.addValueProvider(new CharacterValueProvider(this));
		this.addValueProvider(new DoubleValueProvider(this));
		this.addValueProvider(new FloatValueProvider(this));
		this.addValueProvider(new IntegerValueProvider(this));
		this.addValueProvider(new LongValueProvider(this));
		this.addValueProvider(new ShortValueProvider(this));
		this.addValueProvider(new StringValueProvider(this));
		this.addValueProvider(new EnumValueProvider(this));
		this.addValueProvider(new TimestampValueProvider(this));
		this.addValueProvider(new LocaleValueProvider(this));
		this.addValueProvider(new ListValueProvider(this));
		this.addValueProvider(new SetValueProvider(this));
		this.addValueProvider(new SortedSetValueProvider(this));
		this.addValueProvider(new DefaultMapValueProvider(this));
		this.addValueProvider(new SortedMapValueProvider(this));
	}

	public Class<? extends ValueProvider<?>> fetchValueProviderType(final String fieldName, final Class<?>... types) {
		return this.firstProvider.fetchValueProviderType(fieldName, types);
	}

	public boolean canProvideValue(final String fieldName, final Class<?>... types) {
		return this.firstProvider.canProvideValue(fieldName, types);
	}

	public Object provide(final Class<?>... types) {
		return this.provide("", types);
	}

	public Object provide(final String fieldName, final Class<?>... types) {
		ValueProvisionHandlerPreparer.instance().prepare(this, fieldName, types);
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
