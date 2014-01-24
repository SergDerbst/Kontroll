package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;

public abstract class SimpleValueProvider<V> extends ValueProvider<V> {

	protected SimpleValueProvider(final V initialValue) {
		this.init(super.getInstantiator() != null ? super.getInstantiator().instantiate() : initialValue);
	}

	protected abstract boolean claimSimpleValueResponsibility(final Class<?> valueType);

	@Override
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return types.length == 1 && this.claimSimpleValueResponsibility(types[0]);
	}

	@Override
	public Object provide(final String fieldName, final Class<?>... types) {
		if (this.claimResponsibility(fieldName, types)) {
			final V toProvide = super.getCurrentValue();
			this.increase();
			return toProvide;
		}
		if (super.getNextProvider() == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, types);
		}
		return super.getNextProvider().provide(fieldName, types);
	}

	@SuppressWarnings("unchecked")
	public Object fetchNextValue(final String fieldName, final Object value) {
		if (this.claimResponsibility(fieldName, value.getClass())) {
			return this.makeNextValue((V) value);
		}
		if (super.getNextProvider() == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, value.getClass());
		}
		return ((SimpleValueProvider<?>) super.getNextProvider()).fetchNextValue(fieldName, value);
	}

	@SuppressWarnings("unchecked")
	public void init(final String fieldName, final Object value) {
		if (this.claimResponsibility(fieldName, value.getClass())) {
			super.setInitialValue((V) value);
			this.reset();
			return;
		}
		if (super.getNextProvider() == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, value.getClass());
		}
		((SimpleValueProvider<?>) super.getNextProvider()).init(fieldName, value);
	}
}