package com.tmt.kontroll.test.persistence.dao.entity.value.provision.map.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;

@Component
public class EnumMapValueProviderFactory {

	@Autowired
	ValueProvisionHandler valueProvisionHandler;

	@SuppressWarnings({"rawtypes", "unchecked"})
	public EnumMapValueProvider create(final Class<?> enumKeyType) {
		return new EnumMapValueProvider(enumKeyType, this.valueProvisionHandler);
	}
}
