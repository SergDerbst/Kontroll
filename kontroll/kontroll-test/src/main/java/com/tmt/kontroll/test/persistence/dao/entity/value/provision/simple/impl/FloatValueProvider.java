package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class FloatValueProvider extends SimpleValueProvider<Float> {

	@Override
	protected Float instantiateDefaultValue(final Class<?>... types) {
		return (float) 0;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Float.class.equals(valueType) || Float.TYPE.equals(valueType);
	}

	@Override
	public Float makeNextDefaultValue(final Float value) {
		return value + 1;
	}
}
