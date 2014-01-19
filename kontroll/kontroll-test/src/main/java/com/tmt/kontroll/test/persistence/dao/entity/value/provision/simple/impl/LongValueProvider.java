package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class LongValueProvider extends SimpleValueProvider<Long> {

	public LongValueProvider() {
		super((long) 0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Long.class.equals(valueClass) || Long.TYPE.equals(valueClass);
	}

	@Override
	public Long makeNextValue(final Long value) {
		return value + 1;
	}
}
