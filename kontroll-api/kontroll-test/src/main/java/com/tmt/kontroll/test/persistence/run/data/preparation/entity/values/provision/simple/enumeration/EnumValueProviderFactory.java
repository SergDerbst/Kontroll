package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;


public class EnumValueProviderFactory {

	private static class InstanceHolder {
		public static EnumValueProviderFactory instance = new EnumValueProviderFactory();
	}

	public static EnumValueProviderFactory instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new EnumValueProviderFactory();
		}
		return InstanceHolder.instance;
	}

	public EnumValueProvider create(final ValueProvisionHandler provisionHandler,
	                                final Class<? extends Enum<?>> enumType) {
		return new EnumValueProvider(provisionHandler, enumType);
	}
}
