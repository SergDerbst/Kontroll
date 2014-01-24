package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl;

import java.util.EnumMap;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;


public class EnumMapValueProvider<K extends Enum<K>, V> extends DefaultMapValueProvider<K, V> {

	public EnumMapValueProvider(final Class<K> keyType,
	                            final Class<V> valueType,
	                            final SimpleValueProvisionHandler simpleValueProvisionHandler) {
		super(keyType, valueType, simpleValueProvisionHandler);
	}

	@Override
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return
		types.length == 3 &&
		EnumMap.class.isAssignableFrom(types[0]) &&
		super.claimDefaultResponsibility(fieldName, types);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected EnumMap instantiateEmptyMap() {
		return new EnumMap(super.getKeyType());
	}
}