package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.collection.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.collection.CollectionValueProvider;

public class ListValueProvider extends CollectionValueProvider<Object, List<Object>> {

	public ListValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
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