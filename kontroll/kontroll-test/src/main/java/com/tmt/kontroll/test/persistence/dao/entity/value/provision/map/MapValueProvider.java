package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;

@Component
public abstract class MapValueProvider<K, V, M extends Map<K, V>> extends ValueProvider<M> {

	@Autowired
	ValueProvisionHandler valueProvisionHandler;

	protected abstract boolean claimMapValueResponsibility(final Class<?> mapType, final Class<?> keyType, final Class<?> valueType);

	protected abstract M instantiateEmptyMap();

	@Override
	protected boolean claimDefaultResponsibility(final String fieldName, final Class<?>... types) {
		return types.length == 3 && this.claimMapValueResponsibility(types[0], types[1], types[2]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M makeNextDefaultValue(final M value) {
		final M toIncrease = this.instantiateEmptyMap();
		for (final Entry<K, V> entry : super.getCurrentValue().entrySet()) {
			toIncrease.put((K) this.valueProvisionHandler.fetchNextValue(entry.getKey()), (V) this.valueProvisionHandler.fetchNextValue(entry.getValue()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M instantiateDefaultValue(final Class<?>... types) {
		final M map = this.instantiateEmptyMap();
		map.put((K) this.valueProvisionHandler.provide(types[1]), (V) this.valueProvisionHandler.provide(types[2]));
		return map;
	}

	protected void setValueProvisionHandler(final ValueProvisionHandler valueProvisionHandler) {
		this.valueProvisionHandler = valueProvisionHandler;
	}
}
