package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection;

import java.util.Collection;
import java.util.Iterator;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public abstract class CollectionValueProvider<V, C extends Collection<V>> {

	private C initialCollection;
	private C currentCollection;
	private CollectionValueProvider<?, ?> nextProvider;

	private final SimpleValueProvisionHandler simpleValueProvisionHandler;

	protected CollectionValueProvider(final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		this.simpleValueProvisionHandler = simpleValueProvisionHandler;
	}

	protected abstract boolean isResponsible(final String fieldName, final Class<?> collectionType, final Class<?> itemType);

	protected abstract C instantiateEmptyCollection();

	public boolean canProvideValue(final String fieldName, final Class<?> collectionType, final Class<?> itemType) {
		if (this.isResponsible(fieldName, collectionType, itemType)) {
			return true;
		}
		if (this.nextProvider == null) {
			return false;
		}
		return this.nextProvider.canProvideValue(fieldName, collectionType, itemType);
	}

	@SuppressWarnings("unchecked")
	public Object provide(final String fieldName, final Class<?> collectionType, final Class<?> itemType) {
		if (this.isResponsible(fieldName, collectionType, itemType)) {
			if (this.initialCollection == null) {
				this.init(fieldName, collectionType, itemType);
			}
			final C toProvide = this.currentCollection;
			this.increase((Class<? extends Collection<C>>) collectionType);
			return toProvide;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, collectionType, itemType);
		}
		return this.nextProvider.provide(fieldName, collectionType, itemType);
	}

	@SuppressWarnings("unchecked")
	protected void increase(final Class<? extends Collection<C>> collectionType) {
		final C toIncrease = this.instantiateEmptyCollection();
		final Iterator<? extends Object> iterator = this.currentCollection.iterator();
		while(iterator.hasNext()) {
			final Object value = iterator.next();
			toIncrease.add((V) this.simpleValueProvisionHandler.fetchNextValue(value));
		}
		this.currentCollection = toIncrease;
	}

	@SuppressWarnings("unchecked")
	public void increase(final int steps) {
		for (int i = 0; i < steps; i++) {
			this.increase((Class<? extends Collection<C>>) this.currentCollection.getClass());
		}
		if (this.nextProvider != null) {
			this.nextProvider.increase(steps);
		}
	}

	@SuppressWarnings("unchecked")
	public void init(final String fieldName, final Class<?> collectionType, final Class<?> itemType) {
		if (this.isResponsible(fieldName, collectionType, itemType)) {
			this.initialCollection = this.instantiateEmptyCollection();
			this.initialCollection.add((V) this.simpleValueProvisionHandler.provide(fieldName, itemType));
			this.reset();
			return;
		}
		if (this.nextProvider == null) {
			throw ValueProviderNotFoundException.prepare(fieldName, collectionType, itemType);
		}
		this.nextProvider.init(fieldName, collectionType, itemType);
	}

	public void reset() {
		this.currentCollection = this.initialCollection;
		if (this.nextProvider != null) {
			this.nextProvider.reset();
		}
	}

	public void setNextProvider(final CollectionValueProvider<?, ?> nextProvider) {
		this.nextProvider = nextProvider;
	}

	protected SimpleValueProvisionHandler getSimpleValueProvisionHandler() {
		return this.simpleValueProvisionHandler;
	}
}
