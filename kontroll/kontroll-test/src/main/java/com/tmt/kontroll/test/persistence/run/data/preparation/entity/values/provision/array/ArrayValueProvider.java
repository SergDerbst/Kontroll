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
	protected Class<?>[] prepareTypesFromField(final Object entity,
	                                           final Field field) {
		if (field.getType().isArray()) {
			final Class<?>[] types = new Class<?>[3];
			types[0] = entity.getClass();
			types[1] = field.getType();
			types[2] = field.getType().getComponentType();
			return types;
		}
		return null;
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return types.length == 3 && types[1].isArray() && this.componentType.equals(types[2]);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected C[] makeNextDefaultValue(final Object entity, final Field field, final C[] value) throws Exception {
		final C[] toIncrease = this.instantiateEmptyArray();
		for (int i = 0; i < value.length; i++) {
			toIncrease[i] = (C) this.valueProvisionHandler.provideNextValue(entity, field, value[i]);
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
		array[0] = (C) this.valueProvisionHandler.provide(field, types[0], types[2]);
		return array;
	}
}
