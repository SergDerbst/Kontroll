package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public class ListValueProvider<V> extends CollectionValueProvider<V, List<V>> {

	private final Class<V> valueType;

	public ListValueProvider(final Class<V> valueType, final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		super(simpleValueProvisionHandler);
		this.valueType = valueType;
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> collectionType, final Class<?> itemType) {
		return List.class.isAssignableFrom(collectionType) && this.valueType.equals(itemType);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected List<V> instantiateEmptyCollection() {
		return new ArrayList();
	}
}