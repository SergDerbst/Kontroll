package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.constant;

import java.lang.reflect.Field;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.utils.exceptions.value.provision.ValueHandlingResponsibilityClaimerMissingException;

public class ConstantValueProvider<V> extends ValueProvider<V> {

	private V value;

	public ConstantValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimResponsibility(final Field field, final Class<?>... types) {
		if (super.responsibilityClaimer() == null) {
			throw new ValueHandlingResponsibilityClaimerMissingException();
		}
		return super.responsibilityClaimer().claimResponsibility(field, types);
	}

	@Override
	protected boolean claimDefaultResponsibility(final Field field, final Class<?>... types) {
		return false;
	}

	@Override
	protected V instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return this.value;
	}

	@Override
	protected V makeNextDefaultValue(final Object entity, final Field field, final V value) {
		return this.value;
	}

	public void setValue(final V value) {
		this.value = value;
	}
}
