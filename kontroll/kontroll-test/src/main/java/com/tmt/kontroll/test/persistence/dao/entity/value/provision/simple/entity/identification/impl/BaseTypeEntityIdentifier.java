package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.EntityIdentifier;

public class BaseTypeEntityIdentifier implements EntityIdentifier {

	private final Class<?> baseType;

	public BaseTypeEntityIdentifier(final Class<?> baseType) {
		this.baseType = baseType;
	}

	@Override
	public boolean identify(final Class<?> potentialEntityType) {
		return this.baseType.isAssignableFrom(potentialEntityType);
	}
}