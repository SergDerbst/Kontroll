package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import java.sql.Timestamp;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class TimestampValueProvider extends SimpleValueProvider<Timestamp> {

	public TimestampValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Timestamp instantiateDefaultValue(final Class<?>... types) {
		return new Timestamp(System.currentTimeMillis());
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Timestamp.class.equals(valueType);
	}

	@Override
	public Timestamp makeNextDefaultValue(final Timestamp value) {
		return new Timestamp(value.getTime() + 1);
	}
}
