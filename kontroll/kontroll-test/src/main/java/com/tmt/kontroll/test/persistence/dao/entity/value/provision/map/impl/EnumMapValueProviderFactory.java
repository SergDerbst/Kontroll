package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;

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

	ValueProvisionHandler valueProvisionHandler = ValueProvisionHandler.instance();

	@SuppressWarnings({"rawtypes", "unchecked"})
	public EnumMapValueProvider create(final Class<?> enumKeyType) {
		return new EnumMapValueProvider(enumKeyType, this.valueProvisionHandler);
	}
}
