package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection;

import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.componentOrKeyType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.entityType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.fieldType;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public abstract class CollectionValueProvider<V, C extends Collection<V>> extends ValueProvider<C>{

	protected abstract boolean claimCollectionValueResponsibility(final ValueProvisionKind kind,
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
			types[entityType] = entity.getClass();
			types[fieldType] = field.getType();
			types[componentOrKeyType] = ClassReflectionUtils.retrieveTypeArgumentsOfField(field, 0);
			return types;
		}
		return null;
	}

	@Override
	protected boolean claimDefaultResponsibility(final ValueProvisionKind kind, final Class<?>... types) {
		return types.length == 3 && this.claimCollectionValueResponsibility(kind, types[fieldType], types[componentOrKeyType]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final C value) throws Exception {
		final C toIncrease = this.instantiateEmptyCollection();
		final Iterator<? extends Object> iterator = value.iterator();
		while(iterator.hasNext()) {
			toIncrease.add((V) super.valueProvisionHandler().provideNextZeroDimensionalValue(entity, iterator.next()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected C instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		final C collection = this.instantiateEmptyCollection();
		collection.add((V) super.valueProvisionHandler().provide(kind, types[entityType], types[componentOrKeyType]));
		return collection;
	}
}
