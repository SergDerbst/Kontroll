package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import java.sql.Timestamp;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class TimestampValueProvider extends SimpleValueProvider<Timestamp> {

	private static class InstanceHolder {
		public static TimestampValueProvider instance = new TimestampValueProvider();
	}

	public static TimestampValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new TimestampValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected Timestamp instantiateDefaultValue(final Class<?>... types) {
		return new Timestamp(System.currentTimeMillis());
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Timestamp.class.equals(valueType);
	}

	@Override
	public Timestamp makeNextDefaultValue(final Timestamp value) {
		return new Timestamp(value.getTime() + 1);
	}
}
