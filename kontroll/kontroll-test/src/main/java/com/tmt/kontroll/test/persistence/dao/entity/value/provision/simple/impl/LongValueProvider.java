package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class LongValueProvider extends SimpleValueProvider<Long> {

	public LongValueProvider() {
		super((long) 0);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Long.class.equals(valueType) || Long.TYPE.equals(valueType);
	}

	@Override
	public Long makeNextDefaultValue(final Long value) {
		return value + 1;
	}
}
