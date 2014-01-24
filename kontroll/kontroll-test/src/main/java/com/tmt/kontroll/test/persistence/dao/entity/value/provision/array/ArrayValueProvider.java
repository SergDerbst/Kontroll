package com.tmt.kontroll.test.persistence.dao.entity.value.provision.array;

import java.lang.reflect.Array;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public class ArrayValueProvider<C> {

	private C[] initialArray;
	private C[] currentArray;
	private ArrayValueProvider<?> nextProvider;

	private final Class<C> componentType;
	private final SimpleValueProvisionHandler simpleValueProvisionHandler;

	public ArrayValueProvider(final Class<C> componentType,
	                          final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		this.componentType = componentType;
		this.simpleValueProvisionHandler = simpleValueProvisionHandler;
	}

	protected boolean isResponsible(final String fieldName, final Class<?> componentType) {
		return this.componentType.equals(componentType);
	}

	@SuppressWarnings("unchecked")
	protected C[] instantiateEmptyArray() {
		return (C[]) Array.newInstance(this.componentType, 1);
	}

	public boolean canProvideValue(final String fieldName, final Class<?> componentType) {
		if (this.isResponsible(fieldName, componentType)) {
			return true;
		}
		if (this.nextProvider == null) {
			return false;
		}
		return this.nextProvider.canProvideValue(fieldName, componentType);
	}

	public Object provide(final String fieldName, final Class<?> componentType) {
		if (this.isResponsible(fieldName, componentType)) {
			if (this.initialArray == null) {
				this.init(fieldName, componentType);
			}
			final C[] toProvide = this.currentArray;
			this.increase();
			return toProvide;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, componentType);
		}
		return this.nextProvider.provide(fieldName, componentType);
	}

	@SuppressWarnings("unchecked")
	protected void increase() {
		final C[] toIncrease = this.instantiateEmptyArray();
		for (int i = 0; i < this.currentArray.length; i++) {
			toIncrease[i] = (C) this.simpleValueProvisionHandler.fetchNextValue(this.currentArray[i]);
		}
		this.currentArray = toIncrease;
	}

	public void increase(final int steps) {
		for (int i = 0; i < steps; i++) {
			this.increase();
		}
		if (this.nextProvider != null) {
			this.nextProvider.increase(steps);
		}
	}

	@SuppressWarnings("unchecked")
	public void init(final String fieldName, final Class<?> componentType) {
		if (this.isResponsible(fieldName, componentType)) {
			this.initialArray = this.instantiateEmptyArray();
			this.initialArray[0] = (C) this.simpleValueProvisionHandler.provide(fieldName, componentType);
			this.reset();
			return;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, componentType);
		}
		this.nextProvider.init(fieldName, componentType);
	}

	public void reset() {
		this.currentArray = this.initialArray;
		if (this.nextProvider != null) {
			this.nextProvider.reset();
		}
	}

	public void setNextProvider(final ArrayValueProvider<?> nextProvider) {
		this.nextProvider = nextProvider;
	}

	protected SimpleValueProvisionHandler getSimpleValueProvisionHandler() {
		return this.simpleValueProvisionHandler;
	}
}
