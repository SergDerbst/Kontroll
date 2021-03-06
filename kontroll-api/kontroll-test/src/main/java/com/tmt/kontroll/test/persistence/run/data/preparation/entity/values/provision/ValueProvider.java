package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.incrementation.ValueIncrementor;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.instantiation.ValueInstantiator;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.responsibility.ValueResponsibilityClaimer;
import com.tmt.kontroll.test.persistence.run.utils.exceptions.value.provision.ValueProviderNotFoundException;

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

	protected abstract boolean claimDefaultResponsibility(final ValueProvisionKind kind, final Class<?>... types) throws Exception;

	protected abstract V instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) throws Exception;

	protected abstract V makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final V value, Class<?>... types) throws Exception;

	public boolean canProvideValue(final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		if (this.claimResponsibility(kind, types)) {
			return true;
		}
		if (this.nextProvider == null) {
			return false;
		}
		return this.nextProvider.canProvideValue(kind, types);
	}

	public Object provide(final Object entity,
	                      final ValueProvisionKind kind,
	                      final Class<?>... types) throws Exception {
		if (this.claimResponsibility(kind, types)) {
			if (this.initialValue == null) {
				this.init(entity, kind, types);
			}
			final V toProvide = this.currentValue;
			this.increase(kind, entity, types);
			return toProvide;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithTypes(kind, types);
		}
		return this.nextProvider.provide(entity, kind, types);
	}

	@SuppressWarnings("unchecked")
	public Object fetchNextValue(final Object entity,
	                             final ValueProvisionKind kind,
	                             final Object value,
	                             final Class<?>... types) throws Exception {
		if (this.claimResponsibility(kind, types)) {
			return this.makeNextValue(entity, kind, (V) value, types);
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithValue(entity, kind, value);
		}
		return this.nextProvider.fetchNextValue(entity, kind, value, types);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends ValueProvider<?>> fetchValueProviderType(final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		if (this.claimResponsibility(kind, types)) {
			return (Class<? extends ValueProvider<?>>) this.getClass();
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepareWithTypes(kind, types);
		}
		return this.nextProvider.fetchValueProviderType(kind, types);
	}

	protected void init(final Object entity, final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		this.initialValue = this.instantiator != null ? this.instantiator.instantiate(entity) : this.instantiateDefaultValue(entity, kind, types);
		this.currentValue = this.initialValue;
	}

	protected boolean claimResponsibility(final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		if (this.responsibilityClaimer != null) {
			return this.responsibilityClaimer.claimResponsibility(kind, types);
		}
		return types != null && this.claimDefaultResponsibility(kind, types);
	}

	protected void increase(final ValueProvisionKind kind,
	                        final Object entity, final Class<?>... types) throws Exception {
		this.setCurrentValue(this.makeNextValue(entity, kind, this.currentValue(), types));
	}

	protected V makeNextValue(final Object entity,
	                          final ValueProvisionKind kind,
	                          final V value, final Class<?>... types) throws Exception {
		if (this.incrementor != null) {
			return this.incrementor.increment(value);
		}
		return this.makeNextDefaultValue(entity, kind, value, types);
	}

	protected V currentValue() {
		return this.currentValue;
	}

	protected void setCurrentValue(final V currentValue) {
		this.currentValue = currentValue;
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

	protected ValueIncrementor<V> incrementor() {
		return this.incrementor;
	}

	protected ValueInstantiator<V> instantiator() {
		return this.instantiator;
	}

	protected ValueResponsibilityClaimer responsibilityClaimer() {
		return this.responsibilityClaimer;
	}

	public ValueProvider<?> setNextProvider(final ValueProvider<?> nextProvider) {
		this.nextProvider = nextProvider;
		return this.nextProvider;
	}

	protected ValueProvisionHandler valueProvisionHandler() {
		return this.provisionHandler;
	}

	public void setValueProvisionHandler(final ValueProvisionHandler provisionHandler) {
		this.provisionHandler = provisionHandler;
	}
}