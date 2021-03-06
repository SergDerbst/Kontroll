package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProvider;

public class IntegerIdValueProvider extends IdValueProvider<Integer> {

	public IntegerIdValueProvider(final ValueProvisionHandler provisionHandler,
	                              final Class<?> entityType) {
		super(provisionHandler, entityType);
	}

	@Override
	protected Integer instantiateDefaultValue(final Object entity,
	                                          final ValueProvisionKind kind,
	                                          final Class<?>... types) throws Exception {
		return 1;
	}

	@Override
	protected Integer makeNextDefaultValue(final Object entity,
	                                       final ValueProvisionKind kind,
	                                       final Integer value,
	                                       final Class<?>... types) throws Exception {
		return value + 1;
	}
}
