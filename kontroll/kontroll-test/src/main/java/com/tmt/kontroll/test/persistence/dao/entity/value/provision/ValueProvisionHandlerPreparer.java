package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import java.util.EnumMap;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.array.ArrayValueProviderFactory;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl.EnumMapValueProviderFactory;

public class ValueProvisionHandlerPreparer {

	private static class InstanceHolder {
		public static ValueProvisionHandlerPreparer instance = new ValueProvisionHandlerPreparer();
	}

	public static ValueProvisionHandlerPreparer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ValueProvisionHandlerPreparer();
		}
		return InstanceHolder.instance;
	}

	public void prepare(final ValueProvisionHandler valueProvisionHandler, final String fieldName, final Class<?>... types) {
		if (!valueProvisionHandler.canProvideValue(fieldName, types)) {
			switch (types.length) {
				case 1:
					break;
				case 2:
					if (types[0].isArray()) {
						this.prepareArrayValueProvision(valueProvisionHandler, fieldName, types);
					}
					break;
				case 3:
					this.prepareMapValueProvision(valueProvisionHandler, fieldName, types);
					break;
				default:
					throw ValueProvisionImpossibleException.prepareWithTypes(fieldName, types);
			}
		}
	}

	private void prepareArrayValueProvision(final ValueProvisionHandler valueProvisionHandler, final String fieldName, final Class<?>[] types) {
		valueProvisionHandler.addValueProvider(ArrayValueProviderFactory.instance().create(valueProvisionHandler, types[1]));
	}

	private void prepareMapValueProvision(final ValueProvisionHandler valueProvisionHandler, final String fieldName, final Class<?>... types) {
		if (EnumMap.class.isAssignableFrom(types[0])) {
			valueProvisionHandler.addValueProvider(EnumMapValueProviderFactory.instance().create(valueProvisionHandler, types[1]));
		}
	}
}
