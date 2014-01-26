package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import java.util.EnumMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.array.ArrayValueProviderFactory;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl.EnumMapValueProviderFactory;

@Component
public class ValueProvisionHandlingPreparator {

	@Autowired
	ArrayValueProviderFactory arrayValueProvider;
	@Autowired
	EnumMapValueProviderFactory enumMapValueProviderFactory;

	@Autowired
	ValueProvisionHandler valueProvisionHandler;

	public void prepare(final String fieldName, final Class<?>... types) {
		if (!this.valueProvisionHandler.canProvideValue(fieldName, types)) {
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
					throw ValueProvisionImpossibleException.prepare(fieldName, types);
			}
		}
	}

	private void prepareArrayValueProvision(final String fieldName, final Class<?>[] types) {
		this.valueProvisionHandler.addValueProvider(this.arrayValueProvider.create(types[1]));
	}

	private void prepareMapValueProvision(final String fieldName, final Class<?>... types) {
		if (EnumMap.class.isAssignableFrom(types[0])) {
			this.valueProvisionHandler.addValueProvider(this.enumMapValueProviderFactory.create(types[1]));
		}
	}
}
