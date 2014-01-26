package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.impl;

import javax.persistence.Entity;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.EntityIdentifier;

public class JpaHibernateEntityIdentifier implements EntityIdentifier {

	private static class InstanceHolder {
		public static JpaHibernateEntityIdentifier instance = new JpaHibernateEntityIdentifier();
	}

	public static JpaHibernateEntityIdentifier instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new JpaHibernateEntityIdentifier();
		}
		return  InstanceHolder.instance;
	}

	@Override
	public boolean identify(final Class<?> potentialEntityType) {
		return potentialEntityType.getAnnotation(Entity.class) != null;
	}
}