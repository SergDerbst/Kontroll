package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;

public class ListValueProvider extends CollectionValueProvider<Object, List<Object>> {

	private static class InstanceHolder {
		public static ListValueProvider instance = new ListValueProvider();
	}

	public static ListValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ListValueProvider();
		}
		return InstanceHolder.instance;
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final Class<?> collectionType, final Class<?> itemType) {
		return List.class.isAssignableFrom(collectionType);
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected List instantiateEmptyCollection() {
		return new ArrayList();
	}
}