package com.tmt.kontroll.context.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class RequestHandlingException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= 3225635493934931146L;

	public RequestHandlingException(final Throwable cause) {
		super(cause);
	}
}
