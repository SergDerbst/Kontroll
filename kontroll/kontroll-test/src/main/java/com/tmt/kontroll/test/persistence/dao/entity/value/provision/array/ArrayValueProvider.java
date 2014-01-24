package com.tmt.kontroll.test.persistence.dao.entity.value.provision.array;

import java.lang.reflect.Array;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public class ArrayValueProvider<C> extends ValueProvider<C[]> {

	private final Class<C> componentType;
	private final SimpleValueProvisionHandler simpleValueProvisionHandler;

	public ArrayValueProvider(final Class<C> componentType,
	                          final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		this.componentType = componentType;
		this.simpleValueProvisionHandler = simpleValueProvisionHandler;
	}

	@Override
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return this.componentType.equals(types[0]);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected C[] makeNextDefaultValue(final C[] value) {
		final C[] toIncrease = this.instantiateEmptyArray();
		for (int i = 0; i < super.getCurrentValue().length; i++) {
			toIncrease[i] = (C) this.simpleValueProvisionHandler.fetchNextValue(super.getCurrentValue()[i]);
		}
		return toIncrease;
	}

	@SuppressWarnings("unchecked")
	protected C[] instantiateEmptyArray() {
		return (C[]) Array.newInstance(this.componentType, 1);
	}

	@Override
	public Object provide(final String fieldName, final Class<?>... types) {
		if (super.claimResponsibility(fieldName, types)) {
			if (super.getInitialValue() == null) {
				this.init(fieldName, types[0]);
			}
			final C[] toProvide = super.getCurrentValue();
			this.increase();
			return toProvide;
		}
		if (super.getNextProvider() == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, Array.class, types[0]);
		}
		return super.getNextProvider().provide(fieldName, types);
	}

	@SuppressWarnings("unchecked")
	public void init(final String fieldName, final Class<?>... types) {
		if (super.claimResponsibility(fieldName, types)) {
			super.setInitialValue(this.instantiateEmptyArray());
			super.getInitialValue()[0] = (C) this.simpleValueProvisionHandler.provide(fieldName, types[0]);
			this.reset();
			return;
		}
		if (super.getNextProvider() == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, Array.class, types[0]);
		}
		((ArrayValueProvider<?>) super.getNextProvider()).init(fieldName, types);
	}
}
