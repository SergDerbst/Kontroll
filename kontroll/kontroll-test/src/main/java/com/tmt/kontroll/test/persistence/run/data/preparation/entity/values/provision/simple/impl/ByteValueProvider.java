package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class ByteValueProvider extends SimpleValueProvider<Byte> {

	public ByteValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Byte instantiateDefaultValue(final Object entity, final Field field, final Class<?>... types) {
		return Byte.parseByte("0");
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Field field,
	                                                 final Class<?> valueType) {
		return !field.isAnnotationPresent(Id.class) && Byte.class.equals(valueType) || Byte.TYPE.equals(valueType);
	}

	@Override
	public Byte makeNextDefaultValue(final Object entity, final Field field, final Byte value) {
		return Byte.parseByte(String.valueOf(Integer.parseInt(String.valueOf(value)) + 1));
	}
}
