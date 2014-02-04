package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;

public class SetValueProvider extends CollectionValueProvider<Object, Set<Object>> {

	public SetValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final Class<?> collectionType, final Class<?> itemType) {
		return Set.class.isAssignableFrom(collectionType) && !SortedSet.class.isAssignableFrom(collectionType);
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected Set instantiateEmptyCollection() {
		return new HashSet();
	}
}
