package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapValueProvisionHandler {

	@Autowired
	MapValueProviderFactory providerFactory;

	private MapValueProvider<?, ?, ?> firstProvider;

	public Object provide(final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		return this.provide("", mapType, keyType, valueType);
	}

	public Object provide(final String fieldName, final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		this.prepareForProvision(fieldName, mapType, keyType, valueType);
		return this.firstProvider.provide(fieldName, mapType, keyType, valueType);
	}

	public void reset() {
		if (this.firstProvider != null) {
			this.firstProvider.reset();
		}
	}

	public void addValueProvider(final MapValueProvider<?, ?, ?> valueProvider) {
		valueProvider.setNextProvider(this.firstProvider);
		this.firstProvider = valueProvider;
	}

	private void prepareForProvision(final String fieldName, final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		if (this.firstProvider == null || !this.firstProvider.canProvideValue(fieldName, mapType, keyType, valueType)) {
			this.addValueProvider(this.providerFactory.create(mapType, keyType, valueType));
		}
	}
}
