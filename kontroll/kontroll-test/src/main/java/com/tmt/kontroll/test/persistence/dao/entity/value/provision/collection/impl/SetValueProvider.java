package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;

@Component
public class SetValueProvider extends CollectionValueProvider<Object, Set<Object>> {

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
