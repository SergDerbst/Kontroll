package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity;

import com.tmt.kontroll.test.persistence.dao.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.EntityIdentifier;

public class EntityValueProvider<E> extends SimpleValueProvider<E> {

	private final EntityIdentifier entityIdentifier;
	private final EntityInstanceProvider instanceProvider;

	public EntityValueProvider(final EntityIdentifier entityIdentifier, final EntityInstanceProvider instanceProvider) {
		super(null);
		this.entityIdentifier = entityIdentifier;
		this.instanceProvider = instanceProvider;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueType) {
		if (this.entityIdentifier.identify(valueType)) {
			if (super.getInitialValue() == null) {
				super.init((E) this.instanceProvider.provide(valueType));
			}
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected E makeNextValue(final E value) {
		return (E) this.instanceProvider.provide(value.getClass());
	}
}
