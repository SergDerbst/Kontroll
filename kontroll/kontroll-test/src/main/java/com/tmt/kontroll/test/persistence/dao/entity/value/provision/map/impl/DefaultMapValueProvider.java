package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl;

import java.util.HashMap;
import java.util.Map;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.MapValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

public class DefaultMapValueProvider<K, V> extends MapValueProvider<K, V, Map<K, V>> {

	private final Class<K> keyType;
	private final Class<V> valueType;

	public DefaultMapValueProvider(final Class<K> keyType,
	                               final Class<V> valueType,
	                               final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		super(simpleValueProvisionHandler);
		this.keyType = keyType;
		this.valueType = valueType;
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		return Map.class.isAssignableFrom(mapType) && this.keyType.equals(keyType) && this.valueType.equals(valueType);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected Map<K, V> instantiateEmptyMap() {
		return new HashMap();
	}

	protected Class<K> getKeyType() {
		return this.keyType;
	}

	protected Class<V> getValueType() {
		return this.valueType;
	}
}
