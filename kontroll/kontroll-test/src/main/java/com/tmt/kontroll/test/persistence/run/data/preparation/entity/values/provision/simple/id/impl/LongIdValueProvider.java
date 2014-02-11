package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.impl;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProvider;

public class LongIdValueProvider extends IdValueProvider<Long> {

	public LongIdValueProvider(final ValueProvisionHandler provisionHandler,
	                           final Class<?> entityType) {
		super(provisionHandler, entityType);
	}

	@Override
	protected Long instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) throws Exception {
		return (long) 1;
	}

	@Override
	protected Long makeNextDefaultValue(final Object entity, final Field field, final Long value) throws Exception {
		return value + 1;
	}
}