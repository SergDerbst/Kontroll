package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class StringValueProvider extends SimpleValueProvider<String> {

	public StringValueProvider() {
		super("0");
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return String.class.equals(valueType);
	}

	@Override
	public String makeNextDefaultValue(final String value) {
		return String.valueOf(Integer.parseInt(value) + 1);
	}
}
