package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.impl.JpaHibernateEntityIdentifier;

@Component
public class EntityValueProviderFactory {

	@Autowired
	EntityInstanceProvider instanceProvider;

	@Autowired
	JpaHibernateEntityIdentifier entityIdentifier;

	public EntityValueProvider<?> create() {
		return new EntityValueProvider<>(this.entityIdentifier, this.instanceProvider);
	}
}
