package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import java.util.EnumMap;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.array.ArrayValueProviderFactory;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl.EnumMapValueProviderFactory;

public class ValueProvisionHandlingPreparator {

	private static class InstanceHolder {
		public static ValueProvisionHandlingPreparator instance = new ValueProvisionHandlingPreparator();
	}

	public static ValueProvisionHandlingPreparator instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ValueProvisionHandlingPreparator();
		}
		return InstanceHolder.instance;
	}

	public void prepare(final String fieldName, final Class<?>... types) {
		if (!ValueProvisionHandler.instance().canProvideValue(fieldName, types)) {
			switch (types.length) {
				case 1:
					break;
				case 2:
					if (types[0].isArray()) {
						this.prepareArrayValueProvision(fieldName, types);
					}
					break;
				case 3:
					this.prepareMapValueProvision(fieldName, types);
					break;
				default:
					throw ValueProvisionImpossibleException.prepareWithTypes(fieldName, types);
			}
		}
	}

	private void prepareArrayValueProvision(final String fieldName, final Class<?>[] types) {
		ValueProvisionHandler.instance().addValueProvider(ArrayValueProviderFactory.instance().create(types[1]));
	}

	private void prepareMapValueProvision(final String fieldName, final Class<?>... types) {
		if (EnumMap.class.isAssignableFrom(types[0])) {
			ValueProvisionHandler.instance().addValueProvider(EnumMapValueProviderFactory.instance().create(types[1]));
		}
	}
}
