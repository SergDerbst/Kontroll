package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class EnumMapValueProviderFactory {

	private static class InstanceHolder {
		public static EnumMapValueProviderFactory instance = new EnumMapValueProviderFactory();
	}

	public static EnumMapValueProviderFactory instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new EnumMapValueProviderFactory();
		}
		return InstanceHolder.instance;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public EnumMapValueProvider create(final ValueProvisionHandler valueProvisionHandler, final Class<?> enumKeyType) {
		return new EnumMapValueProvider(enumKeyType, valueProvisionHandler);
	}
}
