package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.collection.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.collection.CollectionValueProvider;

public class SortedSetValueProvider extends CollectionValueProvider<Object, SortedSet<Object>> {

	public SortedSetValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final Class<?> collectionType, final Class<?> itemType) {
		return SortedSet.class.isAssignableFrom(collectionType);
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected SortedSet instantiateEmptyCollection() {
		return new TreeSet();
	}
}