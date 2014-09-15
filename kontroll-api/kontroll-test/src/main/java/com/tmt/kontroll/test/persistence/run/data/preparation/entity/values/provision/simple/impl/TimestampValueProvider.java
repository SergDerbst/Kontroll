package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.sql.Timestamp;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class TimestampValueProvider extends SimpleValueProvider<Timestamp> {

	public TimestampValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Timestamp instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return new Timestamp(System.currentTimeMillis());
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind, final Class<?> valueType) {
		return Timestamp.class.equals(valueType);
	}

	@Override
	public Timestamp makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Timestamp value, final Class<?>... types) {
		return new Timestamp(value.getTime() + 1);
	}
}
