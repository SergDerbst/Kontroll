package com.tmt.kontroll.test.persistence.dao.entity.value.provision.array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArrayValueProvisionHandler {

	@Autowired
	ArrayValueProviderFactory providerFactory;

	private ArrayValueProvider<?> firstProvider;

	public Object provide(final Class<?> componentType) {
		return this.provide("", componentType);
	}

	public Object provide(final String fieldName, final Class<?> componentType) {
		this.prepareForProvision(fieldName, componentType);
		return this.firstProvider.provide(fieldName, componentType);
	}

	public void reset() {
		if (this.firstProvider != null) {
			this.firstProvider.reset();
		}
	}

	public void addValueProvider(final ArrayValueProvider<?> valueProvider) {
		valueProvider.setNextProvider(this.firstProvider);
		this.firstProvider = valueProvider;
	}

	private void prepareForProvision(final String fieldName, final Class<?> componentTypeType) {
		if (this.firstProvider == null || !this.firstProvider.canProvideValue(fieldName, componentTypeType)) {
			this.addValueProvider(this.providerFactory.create(componentTypeType));
		}
	}
}
