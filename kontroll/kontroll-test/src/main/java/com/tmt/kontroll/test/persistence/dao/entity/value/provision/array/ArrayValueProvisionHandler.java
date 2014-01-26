package com.tmt.kontroll.test.persistence.dao.entity.value.provision.array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;

@Component
public class ArrayValueProvisionHandler extends ValueProvisionHandler {

	@Autowired
	ArrayValueProviderFactory providerFactory;

	@Override
	public Object provide(final String fieldName, final Class<?>... types) {
		this.prepareForProvision(fieldName, types);
		return super.getFirstProvider().provide(fieldName, types);
	}
	private void prepareForProvision(final String fieldName, final Class<?>... types) {
		if (super.getFirstProvider() == null || !super.getFirstProvider().canProvideValue(fieldName, types)) {
			this.addValueProvider(this.providerFactory.create(types[0]));
		}
	}
}
