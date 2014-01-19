package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class StringValueProvider extends SimpleValueProvider<String> {

	public StringValueProvider() {
		super("0");
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return String.class.equals(valueClass);
	}

	@Override
	public String makeNextValue(final String value) {
		return String.valueOf(Integer.parseInt(value) + 1);
	}
}
