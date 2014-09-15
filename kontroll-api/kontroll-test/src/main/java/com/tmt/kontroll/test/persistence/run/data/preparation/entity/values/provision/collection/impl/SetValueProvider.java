package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProvider;

public class SetValueProvider extends CollectionValueProvider<Object, Set<Object>> {

	public SetValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final ValueProvisionKind kind,
	                                                     final Class<?> collectionType,
	                                                     final Class<?> itemType) {
		return
		ValueProvisionKind.OneDimensional == kind &&
		Set.class.isAssignableFrom(collectionType) &&
		!SortedSet.class.isAssignableFrom(collectionType);
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected Set instantiateEmptyCollection() {
		return new HashSet();
	}
}
