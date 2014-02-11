package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id;

import java.lang.reflect.Field;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public abstract class IdValueProvider<V> extends SimpleValueProvider<V> {

	private final Class<?> entityType;

	protected IdValueProvider(final ValueProvisionHandler provisionHandler,
	                          final Class<?> entityType) {
		super(provisionHandler);
		this.entityType = entityType;
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) throws Exception {
		return
		field.isAnnotationPresent(Id.class) &&
		this.entityType.equals(types[0]) &&
		types.length == 2 &&
		this.claimSimpleValueResponsibility(field, types[1]);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Field field, final Class<?> valueType) throws Exception {
		return true;
	}
}
