package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class TimestampValueProvider extends SimpleValueProvider<Timestamp> {

	public TimestampValueProvider() {
		super(new Timestamp(System.currentTimeMillis()));
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Timestamp.class.equals(valueClass);
	}

	@Override
	public Timestamp makeNextValue(final Timestamp value) {
		return new Timestamp(value.getTime() + 1);
	}
}
