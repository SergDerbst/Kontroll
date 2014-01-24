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
	protected boolean isResponsible(final String fieldName, final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		return SortedMap.class.isAssignableFrom(mapType) && super.isResponsible(fieldName, mapType, keyType, valueType);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected SortedMap<K, V> instantiateEmptyMap() {
		return new TreeMap();
	}
}