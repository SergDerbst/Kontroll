package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class BooleanValueProvider extends SimpleValueProvider<Boolean> {

	public BooleanValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Boolean instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return false;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Field field,
	                                                 final Class<?> valueType) {
		return (field != null && !field.isAnnotationPresent(Id.class)) && Boolean.class.equals(valueType) || Boolean.TYPE.equals(valueType);
	}

	@Override
	public Boolean makeNextDefaultValue(final Object entity, final Field field, final Boolean value) {
		return !value;
	}
}
