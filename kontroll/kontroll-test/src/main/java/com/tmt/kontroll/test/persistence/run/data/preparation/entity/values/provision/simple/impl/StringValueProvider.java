package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class StringValueProvider extends SimpleValueProvider<String> {

	public StringValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected String instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return "0";
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Field field,
	                                                 final Class<?> valueType) {
		return (field != null && !field.isAnnotationPresent(Id.class)) && String.class.equals(valueType);
	}

	@Override
	public String makeNextDefaultValue(final Object entity, final Field field, final String value) {
		return String.valueOf(Integer.parseInt(value) + 1);
	}
}
