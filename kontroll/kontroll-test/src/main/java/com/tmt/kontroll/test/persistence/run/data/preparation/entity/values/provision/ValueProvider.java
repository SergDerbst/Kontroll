package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.incrementation.ValueIncrementor;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.instantiation.ValueInstantiator;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.responsibility.ValueResponsibilityClaimer;
import com.tmt.kontroll.test.persistence.run.exceptions.data.preparation.entity.value.provision.ValueProviderNotFoundException;

public abstract class ValueProvider<V> {

	private ValueProvisionHandler provisionHandler;
	private ValueIncrementor<V> incrementor;
	private ValueInstantiator<V> instantiator;
	private ValueResponsibilityClaimer responsibilityClaimer;

	private V initialValue;
	private V currentValue;
	private ValueProvider<?> nextProvider;

	protected ValueProvider(final ValueProvisionHandler provisionHandler) {
		this.provisionHandler = provisionHandler;
	}

	protected abstract boolean claimDefaultResponsibility(final Field field, final Class<?>... types) throws Exception;

	protected abstract V instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) throws Exception;

	protected abstract V makeNextDefaultValue(final Object entity, final Field field, final V value) throws Exception;

	@SuppressWarnings("unchecked")
	public Class<? extends ValueProvider<?>> fetchValueProviderType(final Field field, final Class<?>... types) throws Exception {
		if (this.claimResponsibility(field, types)) {
			return (Class<? extends ValueProvider<?>>) this.getClass();
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithTypes(field, types);
		}
		return this.nextProvider.fetchValueProviderType(field, types);
	}

	public boolean canProvideValue(final Field field, final Class<?>... types) throws Exception {
		if (this.claimResponsibility(field, types)) {
			return true;
		}
		if (this.nextProvider == null) {
			return false;
		}
		return this.nextProvider.canProvideValue(field, types);
	}

	public Object provide(final Object entity,
	                      final Field field,
	                      final Class<?>... types) throws Exception {
		if (this.claimResponsibility(field, types)) {
			if (this.initialValue == null) {
				this.init(entity, field, types);
			}
			final V toProvide = this.currentValue;
			this.increase(entity);
			return toProvide;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithTypes(field, types);
		}
		return this.nextProvider.provide(entity, field, types);
	}

	public void init(final Object entity, final Field field, final Class<?>... types) throws Exception {
		if (this.claimResponsibility(field, types)) {
			this.initialValue = this.instantiator != null ? this.instantiator.instantiate(entity) : this.instantiateDefaultValue(entity, field, types);
			this.currentValue = this.initialValue;
			return;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithTypes(field, types);
		}
		this.nextProvider.init(entity, field, types);
	}

	protected void init(final V value) {
		this.initialValue = value;
		this.setCurrentValue(value);
	}

	protected boolean claimResponsibility(final Field field, final Class<?>... types) throws Exception {
		if (this.responsibilityClaimer != null) {
			return this.responsibilityClaimer.claimResponsibility(field, types);
		}
		return this.claimDefaultResponsibility(field, types);
	}

	protected void increase(final Object entity) throws Exception {
		this.setCurrentValue(this.makeNextValue(entity, null, this.getCurrentValue()));
	}

	@SuppressWarnings("unchecked")
	public Object fetchNextValue(final Object entity,
	                             final Field field,
	                             final Object value) throws Exception {
		if (this.claimResponsibility(field, value.getClass())) {
			return this.makeNextValue(entity, field, (V) value);
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithValue(field, value);
		}
		return this.nextProvider.fetchNextValue(entity, field, value);
	}

	protected V makeNextValue(final Object entity,
	                          final Field field,
	                          final V value) throws Exception {
		if (this.incrementor != null) {
			return this.incrementor.increment(value);
		}
		return this.makeNextDefaultValue(entity, field, value);
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

	public void setResponsibilityClaimer(final ValueResponsibilityClaimer responsibilityClaimer) {
		this.responsibilityClaimer = responsibilityClaimer;
	}

	protected ValueIncrementor<V> getIncrementor() {
		return this.incrementor;
	}

	protected ValueInstantiator<V> getInstantiator() {
		return this.instantiator;
	}

	protected ValueResponsibilityClaimer responsibilityClaimer() {
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

	protected ValueProvisionHandler valueProvisionHandler() {
		return this.provisionHandler;
	}

	public void setValueProvisionHandler(final ValueProvisionHandler provisionHandler) {
		this.provisionHandler = provisionHandler;
	}
}