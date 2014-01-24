package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map;

import java.util.EnumMap;
import java.util.Map;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl.DefaultMapValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl.EnumMapValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl.SortedMapValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

@Component
public class MapValueProviderFactory {

	@Autowired
	SimpleValueProvisionHandler simpleValueProvisionHandler;

	@SuppressWarnings({"unchecked", "rawtypes"})
	public <K, V> MapValueProvider<K, V, Map<K, V>> create(final Class<?> mapType, final Class<?> keyType, final Class<?> valueType) {
		if (EnumMap.class.isAssignableFrom(mapType)) {
			return new EnumMapValueProvider(keyType, valueType, this.simpleValueProvisionHandler);
		}
		if (SortedMap.class.isAssignableFrom(mapType)) {
			return new  SortedMapValueProvider(keyType, valueType, this.simpleValueProvisionHandler);
		}
		return new DefaultMapValueProvider(keyType, valueType, this.simpleValueProvisionHandler);
	}
}
