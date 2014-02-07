package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.collection;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;

public abstract class CollectionValueProvider<V, C extends Collection<V>> extends ValueProvider<C>{

	protected abstract boolean claimCollectionValueResponsibility(final Class<?> collectionType, final Class<?> itemType);

	protected abstract C instantiateEmptyCollection();

	protected CollectionValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return types.length == 2 && this.claimCollectionValueResponsibility(types[0], types[1]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C makeNextDefaultValue(final C value) {
		final C toIncrease = this.instantiateEmptyCollection();
		final Iterator<? extends Object> iterator = super.getCurrentValue().iterator();
		while(iterator.hasNext()) {
			toIncrease.add((V) super.getValueProvisionHandler().fetchNextValue(iterator.next()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C instantiateDefaultValue(final Class<?>... types) {
		final C collection = this.instantiateEmptyCollection();
		collection.add((V) super.getValueProvisionHandler().provide(types[1]));
		return collection;
	}
}
