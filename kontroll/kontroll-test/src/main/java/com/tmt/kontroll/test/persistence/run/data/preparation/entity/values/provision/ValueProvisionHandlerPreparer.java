package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import java.lang.reflect.Field;
import java.util.EnumMap;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array.ArrayValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl.EnumMapValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration.EnumValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.utils.exceptions.value.provision.ValueProvisionImpossibleException;

public class ValueProvisionHandlerPreparer {

	private static class InstanceHolder {
		public static ValueProvisionHandlerPreparer instance;
	}

	public static ValueProvisionHandlerPreparer newInstance() {
		InstanceHolder.instance = new ValueProvisionHandlerPreparer();
		return InstanceHolder.instance;
	}

	private ValueProvisionHandlerPreparer() {}

	public void prepare(final ValueProvisionHandler valueProvisionHandler, final Field field, final Class<?>... types) throws Exception {
		if (!valueProvisionHandler.canProvideValue(field, types)) {
			switch (types.length) {
				case 2:
					if (types[1].isEnum()) {
						this.prepareEnumValueProvision(valueProvisionHandler, types);
					}
					if (field.isAnnotationPresent(Id.class)) {
						this.prepareIdValueProvision(valueProvisionHandler, types[0], field.getType());
					}
					break;
				case 3:
					if (types[1].isArray()) {
						this.prepareArrayValueProvision(valueProvisionHandler, types);
					}
					break;
				case 4:
					this.prepareMapValueProvision(valueProvisionHandler, types);
					break;
				default:
					throw ValueProvisionImpossibleException.prepareWithTypes(field, types);
			}
		}
	}

	private void prepareIdValueProvision(final ValueProvisionHandler valueProvisionHandler,
	                                     final Class<?> entityType,
	                                     final Class<?> idType) {
		valueProvisionHandler.addValueProvider(IdValueProviderFactory.instance().create(valueProvisionHandler, entityType, idType));
	}

	@SuppressWarnings("unchecked")
	private void prepareEnumValueProvision(final ValueProvisionHandler valueProvisionHandler, final Class<?>[] types) {
		valueProvisionHandler.addValueProvider(EnumValueProviderFactory.instance().create(valueProvisionHandler, (Class<? extends Enum<?>>) types[1]));
	}

	private void prepareArrayValueProvision(final ValueProvisionHandler valueProvisionHandler, final Class<?>[] types) {
		valueProvisionHandler.addValueProvider(ArrayValueProviderFactory.instance().create(valueProvisionHandler, types[1]));
	}

	private void prepareMapValueProvision(final ValueProvisionHandler valueProvisionHandler, final Class<?>... types) {
		if (EnumMap.class.isAssignableFrom(types[0])) {
			valueProvisionHandler.addValueProvider(EnumMapValueProviderFactory.instance().create(valueProvisionHandler, types[1]));
		}
	}
}
