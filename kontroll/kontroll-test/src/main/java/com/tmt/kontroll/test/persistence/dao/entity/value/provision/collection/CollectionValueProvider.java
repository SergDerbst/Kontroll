package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;

@Component
public abstract class CollectionValueProvider<V, C extends Collection<V>> extends ValueProvider<C>{

	@Autowired
	ValueProvisionHandler valueProvisionHandler;

	protected abstract boolean claimCollectionValueResponsibility(final Class<?> collectionType, final Class<?> itemType);

	protected abstract C instantiateEmptyCollection();

	@Override
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return types.length == 2 && this.claimCollectionValueResponsibility(types[0], types[1]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C makeNextDefaultValue(final C value) {
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
		final C collection = this.instantiateEmptyCollection();
		collection.add((V) this.valueProvisionHandler.provide(types[1]));
		return collection;
	}
}
