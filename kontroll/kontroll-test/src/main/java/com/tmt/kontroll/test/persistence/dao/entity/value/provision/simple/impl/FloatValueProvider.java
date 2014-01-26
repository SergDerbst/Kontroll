package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class FloatValueProvider extends SimpleValueProvider<Float> {

	private static class InstanceHolder {
		public static FloatValueProvider instance = new FloatValueProvider();
	}

	public static FloatValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new FloatValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected Float instantiateDefaultValue(final Class<?>... types) {
		return (float) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Float.class.equals(valueType) || Float.TYPE.equals(valueType);
	}

	@Override
	public Float makeNextDefaultValue(final Float value) {
		return value + 1;
	}
}
