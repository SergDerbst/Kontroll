package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.impl;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.EntityIdentifier;

@Component
public class JpaHibernateEntityIdentifier implements EntityIdentifier {

	@Override
	public boolean identify(final Class<?> potentialEntityType) {
		return potentialEntityType.getAnnotation(Entity.class) != null;
	}
}