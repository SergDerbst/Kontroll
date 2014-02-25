package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array;

import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.componentOrKeyType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.entityType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.fieldType;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

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
			types[entityType] = entity.getClass();
			types[fieldType] = field.getType();
			types[componentOrKeyType] = field.getType().getComponentType();
			return types;
		}
		return null;
	}

	@Override
	protected boolean claimDefaultResponsibility(final ValueProvisionKind kind, final Class<?>... types) {
		return
		ValueProvisionKind.OneDimensional == kind &&
		types[fieldType].isArray() &&
		this.componentType.equals(types[componentOrKeyType]);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected C[] makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final C[] value) throws Exception {
		final C[] toIncrease = this.instantiateEmptyArray();
		for (int i = 0; i < value.length; i++) {
			toIncrease[i] = (C) this.valueProvisionHandler.provideNextZeroDimensionalValue(entity, value[i]);
		}
		return toIncrease;
	}

	@SuppressWarnings("unchecked")
	protected C[] instantiateEmptyArray() {
		return (C[]) Array.newInstance(this.componentType, 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected C[] instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		final C[] array = this.instantiateEmptyArray();
		array[0] = (C) this.valueProvisionHandler.provide(kind, types[entityType], types[componentOrKeyType]);
		return array;
	}
}
