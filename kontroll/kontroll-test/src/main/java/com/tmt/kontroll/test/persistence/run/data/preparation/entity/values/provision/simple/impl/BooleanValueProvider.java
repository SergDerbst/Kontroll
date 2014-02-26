package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class BooleanValueProvider extends SimpleValueProvider<Boolean> {

	public BooleanValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Boolean instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return false;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind,
	                                                 final Class<?> valueType) {
		return
		ValueProvisionKind.Id != kind &&
		(Boolean.class.equals(valueType) || Boolean.TYPE.equals(valueType));
	}

	@Override
	public Boolean makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Boolean value, final Class<?>... types) {
		return !value;
	}
}
