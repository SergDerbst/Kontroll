package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.entity;

import com.tmt.kontroll.test.persistence.run.data.preparation.TestPreparationContext;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.EntityInstanceProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.SimpleValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.simple.entity.identification.impl.JpaHibernateEntityIdentifier;

public class EntityValueProvider extends SimpleValueProvider<Object> {

	public EntityValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Object instantiateDefaultValue(final Class<?>... types) {
		return this.entityInstanceProvider().provide(types[0]);
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		if (JpaHibernateEntityIdentifier.instance().identify(valueType)) {
			if (super.getInitialValue() == null) {
				super.init(this.entityInstanceProvider().provide(valueType));
			}
			return true;
		}
		return false;
	}

	@Override
	protected Object makeNextDefaultValue(final Object value) {
		return this.entityInstanceProvider().provide(value.getClass());
	}

	private EntityInstanceProvider entityInstanceProvider() {
		return TestPreparationContext.instance().entityInstanceProvider();
	}
}