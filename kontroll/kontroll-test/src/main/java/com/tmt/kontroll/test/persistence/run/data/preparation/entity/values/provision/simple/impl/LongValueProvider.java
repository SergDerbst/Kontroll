package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class LongValueProvider extends SimpleValueProvider<Long> {

	public LongValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Long instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return (long) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind,
	                                                 final Class<?> valueType) {
		return
		ValueProvisionKind.Id != kind &&
		(Long.class.equals(valueType) || Long.TYPE.equals(valueType));
	}

	@Override
	public Long makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Long value) {
		return value + 1;
	}
}
