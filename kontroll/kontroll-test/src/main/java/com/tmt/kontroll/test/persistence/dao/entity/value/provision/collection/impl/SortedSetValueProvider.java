package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;

@Component
public class SortedSetValueProvider extends CollectionValueProvider<Object, SortedSet<Object>> {

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