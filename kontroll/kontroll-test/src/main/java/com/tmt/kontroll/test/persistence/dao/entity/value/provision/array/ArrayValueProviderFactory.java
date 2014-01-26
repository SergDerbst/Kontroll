package com.tmt.kontroll.test.persistence.dao.entity.value.provision.array;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;

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

	public ArrayValueProvider<?> create(final Class<?> componentType) {
		return new ArrayValueProvider<>(componentType, ValueProvisionHandler.instance());
	}
}