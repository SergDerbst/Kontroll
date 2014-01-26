package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class DoubleValueProvider extends SimpleValueProvider<Double> {

	private static class InstanceHolder {
		public static DoubleValueProvider instance = new DoubleValueProvider();
	}

	public static DoubleValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new DoubleValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected Double instantiateDefaultValue(final Class<?>... types) {
		return 0.0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Double.class.equals(valueType) || Double.TYPE.equals(valueType);
	}

	@Override
	public Double makeNextDefaultValue(final Double value) {
		return value + 1;
	}
}
