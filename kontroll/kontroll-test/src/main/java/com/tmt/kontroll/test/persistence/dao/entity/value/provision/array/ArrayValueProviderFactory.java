package com.tmt.kontroll.test.persistence.dao.entity.value.provision.array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

@Component
public class ArrayValueProviderFactory {

	@Autowired
	SimpleValueProvisionHandler simpleValueProvisionHandler;

	public ArrayValueProvider<?> create(final Class<?> componentType) {
		return new ArrayValueProvider<>(componentType, this.simpleValueProvisionHandler);
	}
}