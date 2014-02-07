package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.array;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;

public class ArrayValueProvider<C> extends ValueProvider<C[]> {

	private final Class<C> componentType;
	private final ValueProvisionHandler valueProvisionHandler;

	public ArrayValueProvider(final Class<C> componentType,
	                          final ValueProvisionHandler valueProvisionHandler) {
		super(valueProvisionHandler);
		this.componentType = componentType;
		this.valueProvisionHandler = valueProvisionHandler;
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return types[0].isArray() && this.componentType.equals(types[1]);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected C[] makeNextDefaultValue(final C[] value) {
		final C[] toIncrease = this.instantiateEmptyArray();
		for (int i = 0; i < super.getCurrentValue().length; i++) {
			toIncrease[i] = (C) this.valueProvisionHandler.fetchNextValue(super.getCurrentValue()[i]);
		}
		return toIncrease;
	}

	@SuppressWarnings("unchecked")
	protected C[] instantiateEmptyArray() {
		return (C[]) Array.newInstance(this.componentType, 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected C[] instantiateDefaultValue(final Class<?>... types) {
		final C[] array = this.instantiateEmptyArray();
		array[0] = (C) this.valueProvisionHandler.provide(types[1]);
		return array;
	}
}
