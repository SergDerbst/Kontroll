package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.impl.JpaHibernateEntityIdentifier;

@Component
public class EntityValueProvider extends SimpleValueProvider<Object> {

	@Autowired
	EntityInstanceProvider instanceProvider;
	@Autowired
	JpaHibernateEntityIdentifier entityIdentifier;

	@Override
	protected Object instantiateDefaultValue(final Class<?>... types) {
		return this.instanceProvider.provide(types[0]);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		if (this.entityIdentifier.identify(valueType)) {
			if (super.getInitialValue() == null) {
				super.init(this.instanceProvider.provide(valueType));
			}
			return true;
		}
		return false;
	}

	@Override
	protected Object makeNextDefaultValue(final Object value) {
		return this.instanceProvider.provide(value.getClass());
	}
}
