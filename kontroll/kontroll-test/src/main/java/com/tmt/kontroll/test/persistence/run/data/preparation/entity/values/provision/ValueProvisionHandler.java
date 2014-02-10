package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl.ListValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl.SetValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl.SortedSetValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl.DefaultMapValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl.SortedMapValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.BooleanValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.ByteValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.CharacterValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.DoubleValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.FloatValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.IntegerValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.LocaleValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.LongValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.ShortValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.StringValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl.TimestampValueProvider;

public class ValueProvisionHandler {

	private static class InstanceHolder {
		public static ValueProvisionHandler instance;
	}

	public static ValueProvisionHandler newInstance() {
		InstanceHolder.instance = new ValueProvisionHandler();
		return InstanceHolder.instance;
	}

	private final ValueProvisionHandlerPreparer valueProvisionHandlerPreparer;
	private ValueProvider<?> firstProvider;

	private ValueProvisionHandler() {
		this.valueProvisionHandlerPreparer = ValueProvisionHandlerPreparer.newInstance();
		this.setUpValueProvision();
	}

	public void setUpValueProvision() {
		this.addValueProvider(new BooleanValueProvider(this));
		this.addValueProvider(new ByteValueProvider(this));
		this.addValueProvider(new CharacterValueProvider(this));
		this.addValueProvider(new DoubleValueProvider(this));
		this.addValueProvider(new FloatValueProvider(this));
		this.addValueProvider(new IntegerValueProvider(this));
		this.addValueProvider(new LongValueProvider(this));
		this.addValueProvider(new ShortValueProvider(this));
		this.addValueProvider(new StringValueProvider(this));
		this.addValueProvider(new TimestampValueProvider(this));
		this.addValueProvider(new LocaleValueProvider(this));
		this.addValueProvider(new ListValueProvider(this));
		this.addValueProvider(new SetValueProvider(this));
		this.addValueProvider(new SortedSetValueProvider(this));
		this.addValueProvider(new DefaultMapValueProvider(this));
		this.addValueProvider(new SortedMapValueProvider(this));
	}

	public Class<? extends ValueProvider<?>> fetchValueProviderType(final Field field, final Class<?>... types) throws Exception {
		return this.firstProvider.fetchValueProviderType(field, types);
	}

	public boolean canProvideValue(final Field field, final Class<?>... types) throws Exception {
		return this.firstProvider.canProvideValue(field, types);
	}

	public Object provide(final Class<?>... types) throws Exception {
		return this.provide(null, types);
	}

	public Object provide(final Field field, final Class<?>... types) throws Exception {
		return this.provide(types[0].newInstance(), field, types);
	}

	public Object provide(final Object entity, final Field field, final Class<?>... types) throws Exception {
		this.valueProvisionHandlerPreparer.prepare(this, field, types);
		final Object provided = this.firstProvider.provide(entity, field, types);
		return provided;
	}

	public Object fetchNextValue(final Object entity, final Object value) throws Exception {
		return this.fetchNextValue(entity, null, value);
	}

	public Object fetchNextValue(final Object entity, final Field field, final Object value) throws Exception {
		return this.firstProvider.fetchNextValue(entity, field, value);
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
