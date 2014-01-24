package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map;

import java.util.Map;
import java.util.Map.Entry;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public abstract class MapValueProvider<K, V, M extends Map<K, V>> {

	private M initialMap;
	private M currentMap;
	private MapValueProvider<?, ?, ?> nextProvider;

	private final SimpleValueProvisionHandler simpleValueProvisionHandler;

	protected MapValueProvider(final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		this.simpleValueProvisionHandler = simpleValueProvisionHandler;
	}

	protected abstract boolean isResponsible(final String fieldName, final Class<?> mapType, final Class<?> keyType, final Class<?> valueType);

	protected abstract M instantiateEmptyMap();

	public boolean canProvideValue(final String fieldName, final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		if (this.isResponsible(fieldName, mapType, keyType, valueType)) {
			return true;
		}
		if (this.nextProvider == null) {
			return false;
		}
		return this.nextProvider.canProvideValue(fieldName, mapType, keyType, valueType);
	}

	public Object provide(final String fieldName, final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		if (this.isResponsible(fieldName, mapType, keyType, valueType)) {
			if (this.initialMap == null) {
				this.init(fieldName, mapType, keyType, valueType);
			}
			final M toProvide = this.currentMap;
			this.increase();
			return toProvide;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, mapType, keyType, valueType);
		}
		return this.nextProvider.provide(fieldName, mapType, keyType, valueType);
	}

	@SuppressWarnings("unchecked")
	protected void increase() {
		final M toIncrease = this.instantiateEmptyMap();
		for (final Entry<K, V> entry : this.currentMap.entrySet()) {
			toIncrease.put((K) this.simpleValueProvisionHandler.fetchNextValue(entry.getKey()), (V) this.simpleValueProvisionHandler.fetchNextValue(entry.getValue()));
		}
		this.currentMap = toIncrease;
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
	public void init(final String fieldName, final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		if (this.isResponsible(fieldName, mapType, keyType, valueType)) {
			this.initialMap = this.instantiateEmptyMap();
			this.initialMap.put((K) this.simpleValueProvisionHandler.provide(keyType), (V) this.simpleValueProvisionHandler.provide(fieldName, valueType));
			this.reset();
			return;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, mapType, keyType, valueType);
		}
		this.nextProvider.init(fieldName, mapType, keyType, valueType);
	}

	public void reset() {
		this.currentMap = this.initialMap;
		if (this.nextProvider != null) {
			this.nextProvider.reset();
		}
	}

	public void setNextProvider(final MapValueProvider<?, ?, ?> nextProvider) {
		this.nextProvider = nextProvider;
	}

	protected SimpleValueProvisionHandler getSimpleValueProvisionHandler() {
		return this.simpleValueProvisionHandler;
	}
}
