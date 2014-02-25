package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class EnumValueProvider extends SimpleValueProvider<Enum<?>> {

	private final Class<? extends Enum<?>> enumType;

	public EnumValueProvider(final ValueProvisionHandler provisionHandler,
	                         final Class<? extends Enum<?>> enumType) {
		super(provisionHandler);
		this.enumType = enumType;
	}

	@Override
	protected Enum<?> instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return (Enum<?>) types[fieldType].getEnumConstants()[0];
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Field field, final Class<?> valueType) {
		return Enum.class.isAssignableFrom(valueType) && this.enumType.equals(valueType);
	}

	@Override
	protected Enum<?> makeNextDefaultValue(final Object entity, final Field field, final Enum<?> value) {
		return this.getEnumValueFromOrdinal(this.getOrdinalFromEnumValue(value) + 1, value);
	}

	private Enum<?> getEnumValueFromOrdinal(final int i, final Enum<?> value) {
		final Enum<?>[] values=value.getClass().getEnumConstants();
		final int ordinal = i == values.length ? 0 : i;
		return values[ordinal];
	}

	private int getOrdinalFromEnumValue(final Enum<?> value) {
		final Enum<?>[] values = value.getClass().getEnumConstants();
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value) {
				return i;
			}
		}
		return 0;
	}
}