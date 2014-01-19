package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;

public abstract class SimpleValueProvider<V> {

	private V initialValue;
	private V currentValue;
	private SimpleValueProvider<?> nextProvider;

	protected SimpleValueProvider(final V initialValue) {
		this.init(initialValue);
	}

	protected abstract boolean isResponsible(final String fieldName, final Class<?> valueType);

	protected abstract V makeNextValue(final V value);

	public Object provide(final String fieldName, final Class<?> valueType) {
		if (this.isResponsible(fieldName, valueType)) {
			final V toProvide = this.currentValue;
			this.increase();
			return toProvide;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, valueType);
		}
		return this.nextProvider.provide(fieldName, valueType);
	}

	@SuppressWarnings("unchecked")
	public Object fetchNextValue(final String fieldName, final Object value) {
		if (this.isResponsible(fieldName, value.getClass())) {
			return this.makeNextValue((V) value);
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, value.getClass());
		}
		return this.nextProvider.fetchNextValue(fieldName, value);
	}

	public void increase(final int steps) {
		for (int i = 0; i < steps; i++) {
			this.increase();
		}
		if (this.nextProvider != null) {
			this.nextProvider.increase(steps);
		}
	}

	protected void increase() {
		this.setCurrentValue(this.makeNextValue(this.getCurrentValue()));
	}

	protected void init(final V value) {
		this.initialValue = value;
		this.setCurrentValue(value);
	}

	@SuppressWarnings("unchecked")
	public void init(final String fieldName, final Object value) {
		if (this.isResponsible(fieldName, value.getClass())) {
			this.initialValue = (V) value;
			this.reset();
			return;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, value.getClass());
		}
		this.nextProvider.init(fieldName, value);
	}

	public void reset() {
		this.currentValue = this.initialValue;
		if (this.nextProvider != null) {
			this.nextProvider.reset();
		}
	}

	protected V getInitialValue() {
		return this.initialValue;
	}

	protected V getCurrentValue() {
		return this.currentValue;
	}

	protected void setCurrentValue(final V currentValue) {
		this.currentValue = currentValue;
	}

	public void setNextProvider(final SimpleValueProvider<?> nextProvider) {
		this.nextProvider = nextProvider;
	}
}