package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public abstract class CollectionValueProvider<V, C extends Collection<V>> extends ValueProvider<C>{

	protected abstract boolean claimCollectionValueResponsibility(final Field field,
	                                                              final Class<?> collectionType,
	                                                              final Class<?> itemType);

	protected abstract C instantiateEmptyCollection();

	protected CollectionValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return types.length == 3 && this.claimCollectionValueResponsibility(field, types[1], types[2]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C makeNextDefaultValue(final Object entity, final Field field, final C value) throws Exception {
		final C toIncrease = this.instantiateEmptyCollection();
		final Iterator<? extends Object> iterator = super.getCurrentValue().iterator();
		while(iterator.hasNext()) {
			toIncrease.add((V) super.valueProvisionHandler().fetchNextValue(entity, iterator.next()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) throws Exception {
		final C collection = this.instantiateEmptyCollection();
		collection.add((V) super.valueProvisionHandler().provide(types[1]));
		return collection;
	}
}
