package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class IntegerValueProvider extends SimpleValueProvider<Integer> {

	private static class InstanceHolder {
		public static IntegerValueProvider instance = new IntegerValueProvider();
	}

	public static IntegerValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new IntegerValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected Integer instantiateDefaultValue(final Class<?>... types) {
		return 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Integer.class.equals(valueType) || Integer.TYPE.equals(valueType);
	}

	@Override
	public Integer makeNextDefaultValue(final Integer value) {
		return value + 1;
	}
}
