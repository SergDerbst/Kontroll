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
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return
		types.length == 3 &&
		Map.class.isAssignableFrom(types[0]) &&
		this.keyType.equals(types[1]) &&
		this.valueType.equals(types[2]);
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
