package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;

public class SortedSetValueProvider extends CollectionValueProvider<Object, SortedSet<Object>> {

	private static class InstanceHolder {
		public static SortedSetValueProvider instance = new SortedSetValueProvider();
	}

	public static SortedSetValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new SortedSetValueProvider();
		}
		return InstanceHolder.instance;
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