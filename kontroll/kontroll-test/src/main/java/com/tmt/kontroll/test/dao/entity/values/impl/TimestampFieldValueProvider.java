package com.tmt.kontroll.test.dao.entity.values.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class TimestampFieldValueProvider extends ValueFieldProvider<Timestamp> {

	public TimestampFieldValueProvider() {
		super(new Timestamp(System.currentTimeMillis()));
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Timestamp.class.equals(valueClass);
	}

	@Override
	protected Timestamp combineValue(final Timestamp valueOne, final Timestamp valueTwo) {
		return new Timestamp(valueOne.getTime() + valueTwo.getTime());
	}

	@Override
	protected void increase() {
		this.setCurrentValue(new Timestamp(this.getCurrentValue().getTime() + 1));
	}
}
