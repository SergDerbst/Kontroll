package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class ShortValueProvider extends SimpleValueProvider<Short> {

	private static class InstanceHolder {
		public static ShortValueProvider instance = new ShortValueProvider();
	}

	public static ShortValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ShortValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected Short instantiateDefaultValue(final Class<?>... types) {
		return (short) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Short.class.equals(valueType) || Short.TYPE.equals(valueType);
	}

	@Override
	public Short makeNextDefaultValue(final Short value) {
		return (short) (value + 1);
	}
}
