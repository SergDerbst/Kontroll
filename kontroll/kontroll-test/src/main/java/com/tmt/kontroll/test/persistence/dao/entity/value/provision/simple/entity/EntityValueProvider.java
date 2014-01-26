package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity;

import com.tmt.kontroll.test.persistence.dao.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.entity.identification.impl.JpaHibernateEntityIdentifier;

public class EntityValueProvider extends SimpleValueProvider<Object> {

	private static class InstanceHolder {
		public static EntityValueProvider instance = new EntityValueProvider();
	}

	public static EntityValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new EntityValueProvider();
		}
		return  InstanceHolder.instance;
	}

	@Override
	protected Object instantiateDefaultValue(final Class<?>... types) {
		return EntityInstanceProvider.instance().provide(types[0]);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		if (JpaHibernateEntityIdentifier.instance().identify(valueType)) {
			if (super.getInitialValue() == null) {
				super.init(EntityInstanceProvider.instance().provide(valueType));
			}
			return true;
		}
		return false;
	}

	@Override
	protected Object makeNextDefaultValue(final Object value) {
		return EntityInstanceProvider.instance().provide(value.getClass());
	}
}
