package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class LongValueProvider extends SimpleValueProvider<Long> {

	private static class InstanceHolder {
		public static LongValueProvider instance = new LongValueProvider();
	}

	public static LongValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new LongValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected Long instantiateDefaultValue(final Class<?>... types) {
		return (long) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Long.class.equals(valueType) || Long.TYPE.equals(valueType);
	}

	@Override
	public Long makeNextDefaultValue(final Long value) {
		return value + 1;
	}
}
