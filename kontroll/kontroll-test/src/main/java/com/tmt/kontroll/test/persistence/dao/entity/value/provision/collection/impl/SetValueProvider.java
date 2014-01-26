package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;

public class SetValueProvider extends CollectionValueProvider<Object, Set<Object>> {

	private static class InstanceHolder {
		public static SetValueProvider instance = new SetValueProvider();
	}

	public static SetValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new SetValueProvider();
		}
		return InstanceHolder.instance;
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
