package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl;

import java.util.SortedMap;
import java.util.TreeMap;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public class SortedMapValueProvider<K, V> extends DefaultMapValueProvider<K, V> {

	public SortedMapValueProvider(final Class<K> keyType,
	                              final Class<V> valueType,
	                              final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		super(keyType, valueType, simpleValueProvisionHandler);
	}

	@Override
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return
		types.length == 3 &&
		SortedMap.class.isAssignableFrom(types[0]) &&
		super.claimDefaultResponsibility(fieldName, types);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected SortedMap<K, V> instantiateEmptyMap() {
		return new TreeMap();
	}
}