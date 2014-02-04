package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class EnumValueProvider extends SimpleValueProvider<Enum<?>> {

	public EnumValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Enum<?> instantiateDefaultValue(final Class<?>... types) {
		return (Enum<?>) types[0].getEnumConstants()[0];
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Enum.class.isAssignableFrom(valueType);
	}

	@Override
	protected Enum<?> makeNextDefaultValue(final Enum<?> value) {
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