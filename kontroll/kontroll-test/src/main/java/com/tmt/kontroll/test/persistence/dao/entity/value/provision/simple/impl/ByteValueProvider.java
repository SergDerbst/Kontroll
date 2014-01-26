package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class ByteValueProvider extends SimpleValueProvider<Byte> {

	@Override
	protected Byte instantiateDefaultValue(final Class<?>... types) {
		return Byte.parseByte("0");
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final Class<?> valueType) {
		return Byte.class.equals(valueType) || Byte.TYPE.equals(valueType);
	}

	@Override
	public Byte makeNextDefaultValue(final Byte value) {
		return Byte.parseByte(String.valueOf(Integer.parseInt(String.valueOf(value)) + 1));
	}
}
