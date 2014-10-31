package com.tmt.kontroll.business.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class EntityMappingFailedException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= 3476465513289109561L;

	private EntityMappingFailedException(final Throwable cause) {
		super(cause);
	}

	public static EntityMappingFailedException prepare(final Throwable cause) {
		return new EntityMappingFailedException(cause);
	}
}
