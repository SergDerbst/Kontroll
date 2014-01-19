package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

@SuppressWarnings("serial")
public class ValueProviderInvalidArgsException extends ContextedRuntimeException {


	public static ValueProviderInvalidArgsException prepare(final Class<?>... typeArgs) {
		return ValueProviderInvalidArgsException.prepare(null, typeArgs);
	}
	public static ValueProviderInvalidArgsException prepare(final String fieldName, final Class<?>... typeArgs) {
		final ContextedRuntimeException exception = new ValueProviderInvalidArgsException();
		if (fieldName != null) {
			exception.addContextValue("field name", fieldName);
		}
		for (int i = 0; i < typeArgs.length; i++) {
			final String typeArg = typeArgs[i] == null ? null : typeArgs[i].getName();
			exception.addContextValue("type arg " + i, typeArg);
		}
		return (ValueProviderInvalidArgsException) exception;
	}

	public static ValueProviderInvalidArgsException prepare(final Object... valueArgs) {
		return ValueProviderInvalidArgsException.prepare(null, valueArgs);
	}

	public static ValueProviderInvalidArgsException prepare(final String fieldName, final Object... valueArgs) {
		final ContextedRuntimeException exception = new ValueProviderInvalidArgsException();
		if (fieldName != null) {
			exception.addContextValue("field name", fieldName);
		}
		for (int i = 0; i < valueArgs.length; i++) {
			final String valueArg = valueArgs[i] == null ? null : valueArgs[i].getClass().getName();
			exception.addContextValue("type arg " + i, valueArg);
		}
		return (ValueProviderInvalidArgsException) exception;
	}
}
