package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl;

import java.util.EnumMap;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.MapValueProvider;

public class EnumMapValueProvider<E extends Enum<E>> extends MapValueProvider<E, Object, EnumMap<E, Object>> {

	private final Class<E> enumKeyType;

	protected EnumMapValueProvider(final Class<E> enumKeyType,
	                               final ValueProvisionHandler valueProvisionHandler) {
		this.enumKeyType = enumKeyType;
		super.setValueProvisionHandler(valueProvisionHandler);
	}

	@Override
	protected boolean claimMapValueResponsibility(final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		return EnumMap.class.isAssignableFrom(mapType);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected EnumMap instantiateEmptyMap() {
		return new EnumMap(this.enumKeyType);
	}
}