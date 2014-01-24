package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class EnumValueProvider extends SimpleValueProvider<Enum<?>> {

	public EnumValueProvider() {
		super(null);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		if (Enum.class.isAssignableFrom(valueType)) {
			if (super.getInitialValue() == null) {
				super.init((Enum<?>) valueType.getEnumConstants()[0]);
			}
			return true;
		}
		return false;
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