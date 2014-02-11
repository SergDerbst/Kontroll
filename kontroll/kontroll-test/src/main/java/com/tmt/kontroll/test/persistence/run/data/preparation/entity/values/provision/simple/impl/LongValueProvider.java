package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class LongValueProvider extends SimpleValueProvider<Long> {

	public LongValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Long instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return (long) 1;
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Field field,
	                                                 final Class<?> valueType) {
		return !field.isAnnotationPresent(Id.class) && Long.class.equals(valueType) || Long.TYPE.equals(valueType);
	}

	@Override
	public Long makeNextDefaultValue(final Object entity, final Field field, final Long value) {
		return value + 1;
	}
}
