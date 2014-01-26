package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class BooleanValueProvider extends SimpleValueProvider<Boolean> {

	private static class InstanceHolder {
		public static BooleanValueProvider instance = new BooleanValueProvider();
	}

	public static BooleanValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new BooleanValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected Boolean instantiateDefaultValue(final Class<?>... types) {
		return false;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Boolean.class.equals(valueType) || Boolean.TYPE.equals(valueType);
	}

	@Override
	public Boolean makeNextDefaultValue(final Boolean value) {
		return !value;
	}
}
