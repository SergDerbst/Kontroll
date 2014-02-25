package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class ByteValueProvider extends SimpleValueProvider<Byte> {

	public ByteValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Byte instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return Byte.parseByte("0");
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind,
	                                                 final Class<?> valueType) {
		return
		ValueProvisionKind.Id != kind &&
		(Byte.class.equals(valueType) || Byte.TYPE.equals(valueType));
	}

	@Override
	public Byte makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Byte value) {
		return Byte.parseByte(String.valueOf(Integer.parseInt(String.valueOf(value)) + 1));
	}
}
