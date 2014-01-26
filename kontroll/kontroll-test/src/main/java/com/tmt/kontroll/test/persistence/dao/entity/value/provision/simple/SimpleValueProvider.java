package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;

public abstract class SimpleValueProvider<V> extends ValueProvider<V> {

	protected abstract boolean claimSimpleValueResponsibility(final Class<?> valueType);

	@Override
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return types.length == 1 && this.claimSimpleValueResponsibility(types[0]);
	}

	@SuppressWarnings("unchecked")
	public Object fetchNextValue(final Object value) {
		if (super.claimResponsibility("", value.getClass())) {
			return super.makeNextValue((V) value);
		}
		if (super.getNextProvider() == null) {
			throw ValueProviderNotFoundException.prepareWithValue(value);
		}
		return ((SimpleValueProvider<?>) super.getNextProvider()).fetchNextValue(value);
	}
}