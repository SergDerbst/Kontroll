package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public class SetValueProvider<V> extends CollectionValueProvider<V, Set<V>> {

	private final Class<V> valueType;

	@Autowired
	SimpleValueProvisionHandler simpleValueProvisionHandler;

	public SetValueProvider(final Class<V> valueType, final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		super(simpleValueProvisionHandler);
		this.valueType = valueType;
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> collectionType, final Class<?> itemType) {
		return Set.class.isAssignableFrom(collectionType) && !SortedSet.class.isAssignableFrom(collectionType) && this.valueType.equals(itemType);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	protected Set<V> instantiateEmptyCollection() {
		return new HashSet();
	}
}
