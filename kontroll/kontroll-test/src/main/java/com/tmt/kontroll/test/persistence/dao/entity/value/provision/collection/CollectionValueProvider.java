package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection;

import java.util.Collection;
import java.util.Iterator;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;

public abstract class CollectionValueProvider<V, C extends Collection<V>> extends ValueProvider<C>{

	private ValueProvisionHandler valueProvisionHandler;

	protected abstract boolean claimCollectionValueResponsibility(final Class<?> collectionType, final Class<?> itemType);

	protected abstract C instantiateEmptyCollection();

	@Override
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return types.length == 2 && this.claimCollectionValueResponsibility(types[0], types[1]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C makeNextDefaultValue(final C value) {
		this.assureValueProvisionHandler();
		final C toIncrease = this.instantiateEmptyCollection();
		final Iterator<? extends Object> iterator = super.getCurrentValue().iterator();
		while(iterator.hasNext()) {
			toIncrease.add((V) this.valueProvisionHandler.fetchNextValue(iterator.next()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C instantiateDefaultValue(final Class<?>... types) {
		this.assureValueProvisionHandler();
		final C collection = this.instantiateEmptyCollection();
		collection.add((V) this.valueProvisionHandler.provide(types[1]));
		return collection;
	}

	private void assureValueProvisionHandler() {
		if (this.valueProvisionHandler == null) {
			this.valueProvisionHandler = ValueProvisionHandler.instance();
		}
	}
}
