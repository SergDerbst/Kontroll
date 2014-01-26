package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl;

import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.MapValueProvider;

@Component
public class SortedMapValueProvider extends MapValueProvider<Object, Object, SortedMap<Object, Object>> {

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