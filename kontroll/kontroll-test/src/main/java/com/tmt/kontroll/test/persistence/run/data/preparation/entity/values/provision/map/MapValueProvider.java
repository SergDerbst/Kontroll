package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map;

import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.componentOrKeyType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.entityType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.fieldType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.valueType;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

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
			types[entityType] = entity.getClass();
			types[fieldType] = field.getType();
			types[componentOrKeyType] = ClassReflectionUtils.retrieveTypeArgumentsOfField(field, 0);
			types[valueType] = ClassReflectionUtils.retrieveTypeArgumentsOfField(field, 1);
			return types;
		}
		return null;
	}

	@Override
	protected boolean claimDefaultResponsibility(final ValueProvisionKind kind, final Class<?>... types) {
		return ValueProvisionKind.TwoDimensional == kind && this.claimMapValueResponsibility(types[fieldType], types[componentOrKeyType], types[valueType]);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final M value) throws Exception {
		final M toIncrease = this.instantiateEmptyMap();
		for (final Entry<K, V> entry : value.entrySet()) {
			toIncrease.put((K) super.valueProvisionHandler().provideNextZeroDimensionalValue(entity, entry.getKey()), (V) super.valueProvisionHandler().provideNextZeroDimensionalValue(entity, entry.getValue()));
		}
		return toIncrease;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected M instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		final M map = this.instantiateEmptyMap();
		map.put((K) super.valueProvisionHandler().provide(ValueProvisionKind.ZeroDimensional, types[entityType], types[componentOrKeyType]), (V) super.valueProvisionHandler().provide(ValueProvisionKind.ZeroDimensional, types[entityType], types[valueType]));
		return map;
	}
}
