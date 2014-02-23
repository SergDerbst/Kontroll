package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils;
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
	protected Class<?>[] prepareTypesFromField(final Object entity,
	                                           final Field field) {
		if (Collection.class.isAssignableFrom(field.getType())) {
			final Class<?>[] types = new Class<?>[3];
			types[0] = entity.getClass();
			types[1] = field.getType();
			types[2] = ClassReflectionUtils.retrieveTypeArgumentsOfField(field, 0);
			return types;
		}
		return null;
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return types.length == 3 && this.claimCollectionValueResponsibility(field, types[1], types[2]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C makeNextDefaultValue(final Object entity, final Field field, final C value) throws Exception {
		final C toIncrease = this.instantiateEmptyCollection();
		final Iterator<? extends Object> iterator = value.iterator();
		while(iterator.hasNext()) {
			toIncrease.add((V) super.valueProvisionHandler().provideNextValue(entity, field, iterator.next()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) throws Exception {
		final C collection = this.instantiateEmptyCollection();
		collection.add((V) super.valueProvisionHandler().provide(field, types[0], types[2]));
		return collection;
	}
}
