package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id;

import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.entityType;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public abstract class IdValueProvider<V> extends SimpleValueProvider<V> {

	private final Class<?> typeOfEntity;

	protected IdValueProvider(final ValueProvisionHandler provisionHandler,
	                          final Class<?> entityType) {
		super(provisionHandler);
		this.typeOfEntity = entityType;
	}

	@Override
	protected boolean claimDefaultResponsibility(final ValueProvisionKind kind, final Class<?>... types) throws Exception {
		return
		ValueProvisionKind.Id == kind &&
		this.typeOfEntity.equals(types[entityType]);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind, final Class<?> valueType) throws Exception {
		return true;
	}
}
