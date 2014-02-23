package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public abstract class MapValueProvider<K, V, M extends Map<K, V>> extends ValueProvider<M> {

	protected abstract boolean claimMapValueResponsibility(final Class<?> mapType, final Class<?> keyType, final Class<?> valueType);

	protected abstract M instantiateEmptyMap();

	protected MapValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Class<?>[] prepareTypesFromField(final Object entity,
	                                           final Field field) {
		if (Map.class.isAssignableFrom(field.getType())) {
			final Class<?>[] types = new Class<?>[4];
			types[0] = entity.getClass();
			types[1] = field.getType();
			types[2] = ClassReflectionUtils.retrieveTypeArgumentsOfField(field, 0);
			types[3] = ClassReflectionUtils.retrieveTypeArgumentsOfField(field, 1);
			return types;
		}
		return null;
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return types.length == 4 && this.claimMapValueResponsibility(types[1], types[2], types[3]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M makeNextDefaultValue(final Object entity, final Field field, final M value) throws Exception {
		final M toIncrease = this.instantiateEmptyMap();
		for (final Entry<K, V> entry : value.entrySet()) {
			toIncrease.put((K) super.valueProvisionHandler().provideNextValue(entity, field, entry.getKey()), (V) super.valueProvisionHandler().provideNextValue(entity, field, entry.getValue()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) throws Exception {
		final M map = this.instantiateEmptyMap();
		map.put((K) super.valueProvisionHandler().provide(field, types[0], types[2]), (V) super.valueProvisionHandler().provide(field, types[0], types[3]));
		return map;
	}
}
