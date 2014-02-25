package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple;

import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.entityType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.fieldType;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;

public abstract class SimpleValueProvider<V> extends ValueProvider<V> {

	protected SimpleValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Class<?>[] prepareTypesFromField(final Object entity,
	                                           final Field field) {
		final Class<?>[] types = new Class<?>[2];
		types[entityType] = entity.getClass();
		types[fieldType] = field.getType();
		return types;
	}

	protected abstract boolean claimSimpleValueResponsibility(final ValueProvisionKind kind, final Class<?> valueType) throws Exception;

	@Override
	protected boolean claimDefaultResponsibility(final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		return ValueProvisionKind.ZeroDimensional == kind && this.claimSimpleValueResponsibility(kind, types[fieldType]);
	}
}