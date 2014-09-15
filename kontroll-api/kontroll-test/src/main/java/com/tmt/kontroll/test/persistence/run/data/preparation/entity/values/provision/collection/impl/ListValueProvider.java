package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProvider;

public class ListValueProvider extends CollectionValueProvider<Object, List<Object>> {

	public ListValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final ValueProvisionKind kind,
	                                                     final Class<?> collectionType,
	                                                     final Class<?> itemType) {
		return
		ValueProvisionKind.OneDimensional == kind &&
		List.class.isAssignableFrom(collectionType);
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected List instantiateEmptyCollection() {
		return new ArrayList();
	}
}