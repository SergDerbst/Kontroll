package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.map;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;

public abstract class MapValueProvider<K, V, M extends Map<K, V>> extends ValueProvider<M> {

	protected abstract boolean claimMapValueResponsibility(final Class<?> mapType, final Class<?> keyType, final Class<?> valueType);

	protected abstract M instantiateEmptyMap();

	protected MapValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return types.length == 3 && this.claimMapValueResponsibility(types[0], types[1], types[2]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M makeNextDefaultValue(final M value) {
		final M toIncrease = this.instantiateEmptyMap();
		for (final Entry<K, V> entry : super.getCurrentValue().entrySet()) {
			toIncrease.put((K) super.getValueProvisionHandler().fetchNextValue(entry.getKey()), (V) super.getValueProvisionHandler().fetchNextValue(entry.getValue()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M instantiateDefaultValue(final Class<?>... types) {
		final M map = this.instantiateEmptyMap();
		map.put((K) super.getValueProvisionHandler().provide(types[1]), (V) super.getValueProvisionHandler().provide(types[2]));
		return map;
	}
}
