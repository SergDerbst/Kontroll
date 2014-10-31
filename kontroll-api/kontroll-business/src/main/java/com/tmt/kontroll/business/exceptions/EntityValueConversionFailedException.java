package com.tmt.kontroll.business.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class EntityValueConversionFailedException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= -6743519137639660056L;

	private EntityValueConversionFailedException(final Throwable cause) {
		super(cause);
	}

	public static EntityValueConversionFailedException prepare(final Object valueToConvert, final Throwable cause) {
		return (EntityValueConversionFailedException) new EntityValueConversionFailedException(cause).addContextValue("value to convert", valueToConvert);
	}
}
