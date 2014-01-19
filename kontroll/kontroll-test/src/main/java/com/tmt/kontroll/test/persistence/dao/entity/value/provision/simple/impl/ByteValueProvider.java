package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

@Component
public class ByteValueProvider extends SimpleValueProvider<Byte> {

	public ByteValueProvider() {
		super(Byte.parseByte("0"));
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Byte.class.equals(valueClass) || Byte.TYPE.equals(valueClass);
	}

	@Override
	public Byte makeNextValue(final Byte value) {
		return Byte.parseByte(String.valueOf(Integer.parseInt(String.valueOf(value)) + 1));
	}
}
