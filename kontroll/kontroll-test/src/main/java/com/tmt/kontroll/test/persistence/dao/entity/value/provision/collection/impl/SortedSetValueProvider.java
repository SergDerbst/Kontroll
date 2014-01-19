package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public class SortedSetValueProvider<V> extends CollectionValueProvider<V, SortedSet<V>> {

	private final Class<V> valueType;

	public SortedSetValueProvider(final Class<V> valueType, final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		super(simpleValueProvisionHandler);
		this.valueType = valueType;
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> collectionType, final Class<?> itemType) {
		return SortedSet.class.isAssignableFrom(collectionType) && this.valueType.equals(itemType);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	protected SortedSet<V> instantiateEmptyCollection() {
		return new TreeSet();
	}
}