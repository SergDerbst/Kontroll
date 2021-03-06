package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.MapValueProvider;

public class DefaultMapValueProvider extends MapValueProvider<Object, Object, Map<Object, Object>> {

	public DefaultMapValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimMapValueResponsibility(final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		return
		Map.class.isAssignableFrom(mapType) &&
		!EnumMap.class.isAssignableFrom(mapType) &&
		!SortedMap.class.isAssignableFrom(mapType);
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected Map instantiateEmptyMap() {
		return new HashMap();
	}
}
