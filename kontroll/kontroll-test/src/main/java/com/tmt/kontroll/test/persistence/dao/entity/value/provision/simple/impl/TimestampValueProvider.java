package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class TimestampValueProvider extends SimpleValueProvider<Timestamp> {

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
