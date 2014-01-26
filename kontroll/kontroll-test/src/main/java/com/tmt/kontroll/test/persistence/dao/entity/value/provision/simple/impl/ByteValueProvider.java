package com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.impl;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvider;

public class ByteValueProvider extends SimpleValueProvider<Byte> {

	private static class InstanceHolder {
		public static ByteValueProvider instance = new ByteValueProvider();
	}

	public static ByteValueProvider instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ByteValueProvider();
		}
		return  InstanceHolder.instance;
	}

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
