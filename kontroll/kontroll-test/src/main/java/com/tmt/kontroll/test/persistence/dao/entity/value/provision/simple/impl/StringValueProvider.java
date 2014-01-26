package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class StringValueProvider extends SimpleValueProvider<String> {

	private static class InstanceHolder {
		public static StringValueProvider instance = new StringValueProvider();
	}

	public static StringValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new StringValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected String instantiateDefaultValue(final Class<?>... types) {
		return "0";
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return String.class.equals(valueType);
	}

	@Override
	public String makeNextDefaultValue(final String value) {
		return String.valueOf(Integer.parseInt(value) + 1);
	}
}
