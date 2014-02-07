package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.incrementation.ValueIncrementor;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.instantiation.ValueInstantiator;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.responsibility.ValueHandlingResponsibilityClaimer;
import com.tmt.kontroll.test.persistence.run.exceptions.ValueProviderNotFoundException;

public abstract class ValueProvider<V> {

	private ValueProvisionHandler provisionHandler;
	private ValueIncrementor<V> incrementor;
	private ValueInstantiator<V> instantiator;
	private ValueHandlingResponsibilityClaimer responsibilityClaimer;

	private V initialValue;
	private V currentValue;
	private ValueProvider<?> nextProvider;

	protected ValueProvider(final ValueProvisionHandler provisionHandler) {
		this.provisionHandler = provisionHandler;
	}

	protected abstract boolean claimDefaultResponsibility(final Field field, final Class<?>... types);

	protected abstract V instantiateDefaultValue(final Class<?>... types);

	protected abstract V makeNextDefaultValue(final V value);

	@SuppressWarnings("unchecked")
	public Class<? extends ValueProvider<?>> fetchValueProviderType(final Field field, final Class<?>... types) {
		if (this.claimResponsibility(field, types)) {
			return (Class<? extends ValueProvider<?>>) this.getClass();
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithTypes(field, types);
		}
		return this.nextProvider.fetchValueProviderType(field, types);
	}

	public boolean canProvideValue(final Field field, final Class<?>... types) {
		if (this.claimResponsibility(field, types)) {
			return true;
		}
		if (this.nextProvider == null) {
			return false;
		}
		return this.nextProvider.canProvideValue(field, types);
	}

	public Object provide(final Field field, final Class<?>... types) {
		if (this.claimResponsibility(field, types)) {
			if (this.initialValue == null) {
				this.init(field, types);
			}
			final V toProvide = this.currentValue;
			this.increase();
			return toProvide;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithTypes(field, types);
		}
		return this.nextProvider.provide(field, types);
	}

	public void init(final Field field, final Class<?>... types) {
		if (this.claimResponsibility(field, types)) {
			this.initialValue = this.instantiator != null ? this.instantiator.instantiate() : this.instantiateDefaultValue(types);
			this.currentValue = this.initialValue;
			return;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithTypes(field, types);
		}
		this.nextProvider.init(field, types);
	}

	protected void init(final V value) {
		this.initialValue = value;
		this.setCurrentValue(value);
	}

	protected boolean claimResponsibility(final Field field, final Class<?>... types) {
		if (this.responsibilityClaimer != null) {
			return this.responsibilityClaimer.claimResponsibility(field, types);
		}
		return this.claimDefaultResponsibility(field, types);
	}

	protected void increase() {
		this.setCurrentValue(this.makeNextValue(this.getCurrentValue()));
	}

	@SuppressWarnings("unchecked")
	public Object fetchNextValue(final Field field, final Object value) {
		if (this.claimResponsibility(field, value.getClass())) {
			return this.makeNextValue((V) value);
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithValue(field, value);
		}
		return this.nextProvider.fetchNextValue(field, value);
	}

	protected V makeNextValue(final V value) {
		if (this.incrementor != null) {
			return this.incrementor.increment(value);
		}
		return this.makeNextDefaultValue(value);
	}

	public void reset() {
		this.currentValue = null;
		this.initialValue = null;
		if (this.nextProvider != null) {
			this.nextProvider.reset();
		}
	}

	public void setIncrementor(final ValueIncrementor<V> incrementor) {
		this.incrementor = incrementor;
	}

	public void setInstantiator(final ValueInstantiator<V> instantiator) {
		this.instantiator = instantiator;
	}

	public void setResponsibilityClaimer(final ValueHandlingResponsibilityClaimer responsibilityClaimer) {
		this.responsibilityClaimer = responsibilityClaimer;
	}

	protected ValueIncrementor<V> getIncrementor() {
		return this.incrementor;
	}

	protected ValueInstantiator<V> getInstantiator() {
		return this.instantiator;
	}

	protected ValueHandlingResponsibilityClaimer getResponsibilityClaimer() {
		return this.responsibilityClaimer;
	}

	protected ValueProvider<?> getNextProvider() {
		return this.nextProvider;
	}

	public ValueProvider<?> setNextProvider(final ValueProvider<?> nextProvider) {
		this.nextProvider = nextProvider;
		return this.nextProvider;
	}

	protected V getInitialValue() {
		return this.initialValue;
	}

	protected void setInitialValue(final V initialValue) {
		this.initialValue = initialValue;
	}

	protected V getCurrentValue() {
		return this.currentValue;
	}

	protected void setCurrentValue(final V currentValue) {
		this.currentValue = currentValue;
	}

	protected ValueProvisionHandler getValueProvisionHandler() {
		return this.provisionHandler;
	}

	public void setValueProvisionHandler(final ValueProvisionHandler provisionHandler) {
		this.provisionHandler = provisionHandler;
	}
}