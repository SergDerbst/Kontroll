package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

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
		return types.length == 3 && types[1].isArray() && this.componentType.equals(types[2]);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected C[] makeNextDefaultValue(final Object entity, final Field field, final C[] value) throws Exception {
		final C[] toIncrease = this.instantiateEmptyArray();
		for (int i = 0; i < super.getCurrentValue().length; i++) {
			toIncrease[i] = (C) this.valueProvisionHandler.fetchNextValue(entity, super.getCurrentValue()[i]);
		}
		return toIncrease;
	}

	@SuppressWarnings("unchecked")
	protected C[] instantiateEmptyArray() {
		return (C[]) Array.newInstance(this.componentType, 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected C[] instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) throws Exception {
		final C[] array = this.instantiateEmptyArray();
		array[0] = (C) this.valueProvisionHandler.provide(types[1]);
		return array;
	}
}
