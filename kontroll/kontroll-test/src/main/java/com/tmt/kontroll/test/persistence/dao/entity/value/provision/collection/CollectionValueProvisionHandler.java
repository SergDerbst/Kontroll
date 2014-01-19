package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectionValueProvisionHandler {

	@Autowired
	CollectionValueProviderFactory providerFactory;

	private CollectionValueProvider<?, ?> firstProvider;

	public Object provide(final Class<?> collectionType, final Class<?> itemType) {
		return this.provide("", collectionType, itemType);
	}

	public Object provide(final String fieldName, final Class<?> collectionType, final Class<?> itemType) {
		this.prepareForProvision(fieldName, collectionType, itemType);
		return this.firstProvider.provide(fieldName, collectionType, itemType);
	}

	public void reset() {
		if (this.firstProvider != null) {
			this.firstProvider.reset();
		}
	}

	public void addValueProvider(final CollectionValueProvider<?, ?> valueProvider) {
		valueProvider.setNextProvider(this.firstProvider);
		this.firstProvider = valueProvider;
	}

	private void prepareForProvision(final String fieldName, final Class<?> collectionType, final Class<?> itemType) {
		if (this.firstProvider == null || !this.firstProvider.canProvideValue(fieldName, collectionType, itemType)) {
			this.addValueProvider(this.providerFactory.create(collectionType, itemType));
		}
	}
}
