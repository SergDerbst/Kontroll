package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl;

import java.util.SortedMap;
import java.util.TreeMap;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.MapValueProvider;

public class SortedMapValueProvider extends MapValueProvider<Object, Object, SortedMap<Object, Object>> {

	public SortedMapValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimMapValueResponsibility(final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		return SortedMap.class.isAssignableFrom(mapType);
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected SortedMap instantiateEmptyMap() {
		return new TreeMap();
	}
}