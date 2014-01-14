package com.tmt.kontroll.test.dao.entity.values;


public abstract class ValueFieldProvider<V> {

	private V initialValue;
	private V currentValue;
	private V offsetValue;
	private ValueFieldProvider<?> nextProvider;

	protected ValueFieldProvider(final V initialValue) {
		this.init(null, initialValue);
	}

	protected abstract boolean isResponsible(final String fieldName, final Class<?> valueClass);

	protected abstract V combineValue(final V valueOne, final V valueTwo);

	protected abstract void increase();

	public Object provide(final String fieldName, final Class<?> valueClass) {
		if (this.isResponsible(fieldName, valueClass)) {
			final V provideValue = this.currentValue;
			this.increase();
			if (this.offsetValue != null) {
				return this.combineValue(this.offsetValue, provideValue);
			}
			return provideValue;
		}
		if (this.nextProvider == null) {
			throw new RuntimeException("No field value provider found for " + valueClass.getName());
		}
		return this.nextProvider.provide(fieldName, valueClass);
	}

	@SuppressWarnings("unchecked")
	public void offset(final String fieldName, final Object offsetValue) {
		if (this.isResponsible(fieldName, offsetValue.getClass())) {
			this.offsetValue = (V) offsetValue;
			return;
		}
		if (this.nextProvider == null) {
			throw new RuntimeException("No value provider found for " + offsetValue.getClass().getName());
		}
		this.nextProvider.offset(fieldName, offsetValue);
	}

	public void increase(final int steps) {
		for (int i = 0; i < steps; i++) {
			this.increase();
		}
		if (this.nextProvider != null) {
			this.nextProvider.increase(steps);
		}
	}

	@SuppressWarnings("unchecked")
	public void init(final String fieldName, final Object initialValue) {
		if (this.isResponsible(fieldName, initialValue.getClass())) {
			this.initialValue = (V) initialValue;
			this.reset();
			return;
		}
		if (this.nextProvider == null) {
			throw new RuntimeException("No field value provider found for " + initialValue.getClass().getName());
		}
		this.nextProvider.init(fieldName, initialValue);
	}

	public void reset() {
		this.currentValue = this.initialValue;
		if (this.nextProvider != null) {
			this.nextProvider.reset();
		}
	}

	public void setNextProvider(final ValueFieldProvider<?> nextProvider) {
		this.nextProvider = nextProvider;
	}

	protected V getCurrentValue() {
		return this.currentValue;
	}

	protected void setCurrentValue(final V currentValue) {
		this.currentValue = currentValue;
	}
}