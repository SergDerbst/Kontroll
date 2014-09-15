package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProvider;

public class SortedSetValueProvider extends CollectionValueProvider<Object, SortedSet<Object>> {

	public SortedSetValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final ValueProvisionKind kind,
	                                                     final Class<?> collectionType,
	                                                     final Class<?> itemType) {
		return
		ValueProvisionKind.OneDimensional == kind &&
		SortedSet.class.isAssignableFrom(collectionType);
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected SortedSet instantiateEmptyCollection() {
		return new TreeSet();
	}
}