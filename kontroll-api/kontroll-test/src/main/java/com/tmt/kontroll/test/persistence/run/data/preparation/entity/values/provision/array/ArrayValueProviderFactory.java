package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;

public class ArrayValueProviderFactory {

	private static class InstanceHolder {
		public static ArrayValueProviderFactory instance = new ArrayValueProviderFactory();
	}

	public static ArrayValueProviderFactory instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ArrayValueProviderFactory();
		}
		return InstanceHolder.instance;
	}

	public <C> ArrayValueProvider<C> create(final ValueProvisionHandler valueProvisionHandler, final Class<C> componentType) {
		return new ArrayValueProvider<>(componentType, valueProvisionHandler);
	}
}