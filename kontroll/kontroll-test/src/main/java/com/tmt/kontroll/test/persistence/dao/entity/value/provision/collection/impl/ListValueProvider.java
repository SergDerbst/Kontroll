package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;

@Component
public class ListValueProvider extends CollectionValueProvider<Object, List<Object>> {

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