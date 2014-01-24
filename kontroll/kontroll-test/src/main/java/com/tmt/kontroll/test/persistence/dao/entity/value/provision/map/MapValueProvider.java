package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map;

import java.util.Map;
import java.util.Map.Entry;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public abstract class MapValueProvider<K, V, M extends Map<K, V>> extends ValueProvider<M> {

	private M initialMap;
	private M currentMap;
	private MapValueProvider<?, ?, ?> nextProvider;

	private final SimpleValueProvisionHandler simpleValueProvisionHandler;

	protected MapValueProvider(final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		this.simpleValueProvisionHandler = simpleValueProvisionHandler;
	}

	protected abstract M instantiateEmptyMap();

	@Override
	public Object provide(final String fieldName, final Class<?>... types) {
		if (super.claimResponsibility(fieldName, types)) {
			if (this.initialMap == null) {
				this.init(fieldName, types[0], types[1], types[2]);
			}
			final M toProvide = this.currentMap;
			this.increase();
			return toProvide;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, types);
		}
		return this.nextProvider.provide(fieldName, types);
	}

	protected void increase() {
		this.currentMap = super.makeNextValue(this.currentMap);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M makeNextDefaultValue(final M value) {
		final M toIncrease = this.instantiateEmptyMap();
		for (final Entry<K, V> entry : this.currentMap.entrySet()) {
			toIncrease.put((K) this.simpleValueProvisionHandler.fetchNextValue(entry.getKey()), (V) this.simpleValueProvisionHandler.fetchNextValue(entry.getValue()));
		}
		return toIncrease;
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
	public void init(final String fieldName, final Class<?>... types) {
		if (super.claimResponsibility(fieldName, types)) {
			this.initialMap = this.instantiateEmptyMap();
			this.initialMap.put((K) this.simpleValueProvisionHandler.provide(types[1]), (V) this.simpleValueProvisionHandler.provide(fieldName, types[2]));
			this.reset();
			return;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, types);
		}
		this.nextProvider.init(fieldName, types);
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
