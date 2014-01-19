package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class FloatValueProvider extends SimpleValueProvider<Float> {

	public FloatValueProvider() {
		super((float) 0);
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Float.class.equals(valueClass) || Float.TYPE.equals(valueClass);
	}

	@Override
	public Float makeNextValue(final Float value) {
		return value + 1;
	}
}
