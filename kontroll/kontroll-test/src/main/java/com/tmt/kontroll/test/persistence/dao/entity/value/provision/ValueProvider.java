package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import com.tmt.kontroll.test.persistence.dao.entity.value.incrementation.ValueIncrementor;
import com.tmt.kontroll.test.persistence.dao.entity.value.instantiation.ValueInstantiator;
import com.tmt.kontroll.test.persistence.dao.entity.value.responsibility.ValueHandlingResponsibilityClaimer;

public abstract class ValueProvider<V> {

	private ValueIncrementor<V> incrementor;
	private ValueInstantiator<V> instantiator;
	private ValueHandlingResponsibilityClaimer responsibilityClaimer;

	private V initialValue;
	private V currentValue;
	private ValueProvider<?> nextProvider;

	public abstract Object provide(final String fieldName, final Class<?>... types);

	protected abstract boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types);

	protected abstract V makeNextDefaultValue(final V value);

	public boolean canProvideValue(final String fieldName, final Class<?>... types) {
		if (this.claimResponsibility(fieldName, types)) {
			return true;
		}
		if (this.nextProvider == null) {
			return false;
		}
		return this.nextProvider.canProvideValue(fieldName, types);
	}

	protected void init(final V value) {
		this.initialValue = value;
		this.setCurrentValue(value);
	}

	protected boolean claimResponsibility(final String fieldName, final Class<?>... types) {
		if (this.responsibilityClaimer != null) {
			return this.responsibilityClaimer.claimResponsibility(fieldName, types);
		}
		return this.claimDefaultResponsibility(fieldName, types[0]);
	}

	protected V makeNextValue(final V value) {
		if (this.incrementor != null) {
			return this.incrementor.increment(value);
		}
		return this.makeNextDefaultValue(value);
	}

	public void increase(final int steps) {
		for (int i = 0; i < steps; i++) {
			this.increase();
		}
		if (this.getNextProvider() != null) {
			this.getNextProvider().increase(steps);
		}
	}

	protected void increase() {
		this.setCurrentValue(this.makeNextValue(this.getCurrentValue()));
	}

	public void reset() {
		this.currentValue = this.initialValue;
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

	public void setNextProvider(final ValueProvider<?> nextProvider) {
		this.nextProvider = nextProvider;
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
}